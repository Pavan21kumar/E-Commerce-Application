package com.retail.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class PleaseGiveRefreshAccessTokenRequest extends RuntimeException {

	private String message;
}
