package com.azure.keyvaluts.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum for all the supported encrypting algorithms
 *
 */
public enum AlgoScheme {
	AES_CBC_PKCS5PADDING("AES", "AES/CBC/PKCS5Padding", 16, 16, 16),
	AES_ECB_PKCS5PADDING("AES", "AES/ECB/PKCS5Padding", 16, 0, 16),
	DES_CBC_PKCS5PADDING("DES", "DES/CBC/PKCS5PADDING", 16, 8, 8),
	DES_ECB_PKCS5PADDING("DES", "DES/ECB/PKCS5PADDING", 16, 0, 8),
	TRIPLE_DES_CBC_PKCS5PADDING("DESede", "DESede/CBC/PKCS5PADDING", 16, 8, 24);

	private String algoName;
	private String scheme;
	private int blockSize;
	private int ivLen;
	private int keyLen;

	private AlgoScheme(String algoName, String scheme, int blockSize, int ivLen, int keyLen) {
		this.algoName = algoName;
		this.scheme = scheme;
		this.blockSize = blockSize;
		this.ivLen = ivLen;
		this.keyLen = keyLen;
	}

	public String getAlgoName() {
		return this.algoName;
	}

	public String getScheme() {
		return this.scheme;
	}

	public int getBlockSize() {
		return this.blockSize;
	}

	public int getIvLen() {
		return this.ivLen;
	}

	public int getKeyLen() {
		return this.keyLen;
	}

	static final Map<AlgoScheme, String> Algolookup = new HashMap<>();
	static {
		for (AlgoScheme as : AlgoScheme.values()) {
			Algolookup.put(as, as.toString());
		}
	}

	public static AlgoScheme getAlgoScheme(String algoName) {
		for (AlgoScheme as : AlgoScheme.values()) {
			if (as.toString().equalsIgnoreCase(algoName)) {
				return as;
			}
		}
		return null;
	}
}
