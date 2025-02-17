package com.retail.ecommerce.mailservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MessageModel {

	private String to;
	private String subject;
	private String text;
	

}
