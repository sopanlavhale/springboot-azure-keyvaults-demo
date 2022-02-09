package com.azure.keyvaluts.model;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserRequest {
	private List<DataValues> dataValues;
	private List<String> encryptdKey;

}
