package com.azure.keyvaluts.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component("applicationProperties")
public class ApplicationProperties {

  private static final Logger LOG = LogManager.getLogger(ApplicationProperties.class);
  
  @Value("${spring-azure-symmetric-key}")
  private String keys;
  
//  @PostConstruct
//  public void construct() throws Exception {
//    LOG.info(LogFormatter.instance()
//        .message("Constructing " + this.getClass().getSimpleName() + " ...").format());
//  }
//
//  @PreDestroy
//  public void cleanUp() throws Exception {
//    LOG.info(LogFormatter.instance()
//        .message("Cleaning up " + this.getClass().getSimpleName() + " ...").format());
//  }
  
}
