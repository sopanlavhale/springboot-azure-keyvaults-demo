package com.azure.keyvaluts.common;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class Encoding {

	/**
	 * Encode the input byte using the a specific algorithm
	 * 
	 * @param UnencodedByte
	 * @param encodealgo
	 * @return Encoded value
	 */
	public String encode(byte[] UnencodedByte, String encodealgo) {

		if (SupportedEncoding.BASE64.value().equalsIgnoreCase(encodealgo)) {
			return Base64.encodeBase64String(UnencodedByte);
		} else if (SupportedEncoding.HEX.value().equalsIgnoreCase(encodealgo)) {
			return Hex.encodeHexString(UnencodedByte);
		}
		return new String(UnencodedByte, StandardCharsets.UTF_8);
	}

	/**
	 * Decode the input data using the specific algorithm
	 * 
	 * @param encodedData
	 * @param encodealgo
	 * @return Decoded data (in bytes)
	 * @throws Exception
	 */
	public byte[] decode(String encodedData, String encodealgo) throws Exception {
		try {
			if (SupportedEncoding.BASE64.value().equalsIgnoreCase(encodealgo)) {
				return Base64.decodeBase64(encodedData);
			} else if (SupportedEncoding.HEX.value().equalsIgnoreCase(encodealgo)) {
				return Hex.decodeHex(encodedData.toCharArray());
			}

			return encodedData.getBytes(StandardCharsets.UTF_8);
		} catch (DecoderException e) {
			throw new Exception(e);
		}
	}

}
