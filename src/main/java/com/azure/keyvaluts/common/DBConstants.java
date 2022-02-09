package com.azure.keyvaluts.common;

public class DBConstants {

	public enum FieldsName {
		FIRSTNAME("firstName"), LASTNAME("lastName"), EMAIL("email"), MOBILE("mobile"),
		PASSWORD("password"), ACCOUNTNUMBER("accountNumber"),PIN("pin"),CARDNUMBER("cardNumber");
		
		private String bucket;
		
		private FieldsName(String b) {
			this.bucket = b;
		}
		
		public String value() {
			return this.bucket;
		}
		
		public boolean equals(String bucket) {
			return this.bucket == bucket;
		}
		
	}

}
