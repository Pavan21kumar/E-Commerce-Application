package com.retail.ecommerce.exceptionhandler;

import java.net.ConnectException;

import org.eclipse.angus.mail.util.MailConnectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.retail.ecommerce.exception.AccessTokenExpireException;
import com.retail.ecommerce.exception.EmailAllreadyPresentException;
import com.retail.ecommerce.exception.InvalidCreadentials;
import com.retail.ecommerce.exception.InvalidEmailException;
import com.retail.ecommerce.exception.InvalidOTPException;
import com.retail.ecommerce.exception.OtpExpaireException;
import com.retail.ecommerce.exception.PleaseGiveRefreshAccessTokenRequest;
import com.retail.ecommerce.exception.RegistrationSessionExpaireException;
import com.retail.ecommerce.exception.RoleNotSpecifyException;
import com.retail.ecommerce.exception.UserAllreadyPresentException;
import com.retail.ecommerce.exception.UserIsAllreadyLoginException;
import com.retail.ecommerce.exception.UserIsNotLoginException;
import com.retail.ecommerce.util.ErrorStructure;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class AuthCustomeExceptionHandler {

	private ErrorStructure<String> errorStructure;

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlingUserAllreadyPresentException(UserAllreadyPresentException e) {
		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setMessage("user is allready present please enter Unique UserName").setRootCouse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlingEmailAllreadyPresentException(
			EmailAllreadyPresentException e) {
		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setMessage("Email is allready present please enter Unique UserName").setRootCouse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlingRoleNotSpecifyException(RoleNotSpecifyException e) {
		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setMessage("Role is Not present please enter Role To The User").setRootCouse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlingInvalidOTPException(InvalidOTPException e) {
		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setMessage("invalid otp ..please enter correct otp....").setRootCouse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlingOtpExpaireException(OtpExpaireException e) {
		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setMessage("invalid otp ..Otp Is  eExpaired....").setRootCouse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlingRegistrationSessionExpaireException(
			RegistrationSessionExpaireException e) {
		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setMessage("User registration Time Expaired...Pleace Try Again......").setRootCouse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlingInvalidEmailException(InvalidEmailException e) {
		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setMessage("Invalid Email..olease enter Correct Email.........").setRootCouse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlingConnectException(ConnectException e) {
		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setMessage("please check Network.........").setRootCouse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlingMailConnectException(MailConnectException e) {
		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setMessage("please check Network.........").setRootCouse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlingInvalidCreadentialsException(InvalidCreadentials e) {
		return ResponseEntity.badRequest()
				.body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
						.setMessage("Invalid Creadentials.. please enter correct Details...........")
						.setRootCouse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlingUserIsNotLoginException(UserIsNotLoginException e) {
		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setMessage("User Is Not Login. please Login First.............").setRootCouse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlingUserIsAllreadyLoginException(UserIsAllreadyLoginException e) {
		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
				.setMessage("User Is Allready Login.............").setRootCouse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlingAccessTokenExpireException(AccessTokenExpireException e) {
		return ResponseEntity.badRequest()
				.body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
						.setMessage("AccessToken Is expired Please Re Generate AccessToken.............")
						.setRootCouse(e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlingPleaseGiveRefreshAccessTokenRequest(
			PleaseGiveRefreshAccessTokenRequest e) {
		return ResponseEntity.badRequest()
				.body(errorStructure.setStatusCode(HttpStatus.BAD_REQUEST.value())
						.setMessage("AccessToken Is expired Please Re Generate AccessToken.............")
						.setRootCouse(e.getMessage()));
	}

}
