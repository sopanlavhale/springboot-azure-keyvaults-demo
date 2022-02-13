package com.azure.keyvaluts.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class LogFormatter {

  private static ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    // if (Optional.ofNullable(System.getenv("PRETTY_PRINT")).map(Boolean::valueOf).orElse(false)) {
    // objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    // }

    objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
  }
    
  public enum Mask {
    //@formatter:off 
    EMAIL_MASKED("#########"),
    PASSWORD_MASKED("********"),
    PIN_MASKED("$$$$$$$$"), 
    KEY_MASKED("@@@@@@@@"),
    MASKED("@@@@@@@@");;
    
    //@formatter:on  
    private String value;

    private Mask(String value) {
      this.value = value;
    }

    public String value() {
      return this.value;
    }

    public boolean equals(String value) {
      return this.value.equals(value);
    }
  }

  private Map<String, Object> data = new HashMap<String, Object>();
  private String msg;
  private String traceId;
  
  protected LogFormatter() {}

  protected LogFormatter(String traceId) {
    this.traceId = traceId;
  }

  public static LogFormatter instance() {
    return new LogFormatter();
  }

  public static LogFormatter instance(String traceId) {
    return new LogFormatter(traceId);
  }

  // @TODO - Might want to accept explicity value types
  public LogFormatter data(String key, Object value) {
    List<String> masked = Arrays.asList("pin", "password", "key");
    if (masked.contains(key)) {
      data.put(key, Mask.MASKED.value());
    } else {
      data.put(key, value);
    }
    return this;
  }
    
  public LogFormatter maskedData(String key, String maskedConstant) {
	    this.data.put(key, maskedConstant);
	    return this;
  }	

  public LogFormatter message(String msg) {
    this.msg = msg;
    return this;
  }

  public String format() {
    // @NOTE - FORMAT => msg == {data json}
    StringBuffer response = new StringBuffer();
    if (!StringUtils.isEmpty(this.traceId))
      response.append("[").append(traceId).append("]: ");
    if (!StringUtils.isEmpty(this.msg))
      response.append(this.msg).append(" == ");

    if (!this.data.isEmpty()) {
      try {
        response.append(objectMapper.writeValueAsString(data));
      } catch (JsonProcessingException e) {
        // TODO Auto-generated catch block
      }
    }

    return response.toString();
  }
}
