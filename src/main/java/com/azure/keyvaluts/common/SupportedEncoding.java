package com.azure.keyvaluts.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Will list out all the supported encoding algorithm
 * 
 */
public enum SupportedEncoding {
	HEX("HEX"), BASE64("BASE64"), UTF8("UTF8");

	private String value;

	private SupportedEncoding(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	static Map<SupportedEncoding, String> Encodelookup = new HashMap<>();
	static {
		for (SupportedEncoding s : SupportedEncoding.values()) {
			Encodelookup.put(s, s.value());
		}
	}
}
