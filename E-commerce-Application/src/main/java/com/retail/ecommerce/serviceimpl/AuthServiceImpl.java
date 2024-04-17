package com.retail.ecommerce.serviceimpl;

import java.text.DecimalFormat;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.retail.ecommerce.cache.CacheStore;
import com.retail.ecommerce.entity.Customer;
import com.retail.ecommerce.entity.Seller;
import com.retail.ecommerce.entity.User;
import com.retail.ecommerce.enums.UserRole;
import com.retail.ecommerce.mailservice.MailService;
import com.retail.ecommerce.mailservice.MessageModel;
import com.retail.ecommerce.repository.UserRegisterRepoository;
import com.retail.ecommerce.requestdto.UserRequest;
import com.retail.ecommerce.responsedto.OtpRequest;
import com.retail.ecommerce.responsedto.UserResponse;
import com.retail.ecommerce.service.AuthService;
import com.retail.ecommerce.util.EmailAllreadyPresentException;
import com.retail.ecommerce.util.InvalidEmailException;
import com.retail.ecommerce.util.InvalidOTPException;
import com.retail.ecommerce.util.OtpExpaireException;
import com.retail.ecommerce.util.RegistrationSessionExpaireException;
import com.retail.ecommerce.util.ResponseStructure;
import com.retail.ecommerce.util.RoleNotSpecifyException;
import com.retail.ecommerce.util.SimpleResponseStructure;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	private UserRegisterRepoository userRepo;
	private CacheStore<String> otpCache;
	private CacheStore<User> userCache;
	private ResponseStructure<UserResponse> responseStructure;
	private SimpleResponseStructure otpStructure;
	private MailService mailService;

	@Override
	public ResponseEntity<SimpleResponseStructure> userRegister(UserRequest userRequest) {

		if (userRepo.existsByEmail(userRequest.getEmail()))
			throw new EmailAllreadyPresentException("User Is allready Present..");
		User user = mapToUser(userRequest);
		String otp = ganerateOTP();
		otpCache.add(user.getEmail(), otp);
		userCache.add(user.getEmail(), user);

		try {
			sendMail(user, otp);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block

			throw new InvalidEmailException("Email Is Invalid");
		}

		// userRepo.save(user);

		return ResponseEntity.ok(otpStructure.setStatusCode(HttpStatus.ACCEPTED.value()).setMessage(
				"verify otp through the Email and Complete Registration" + " OTP will Expaire Within 2 Minutes"));
	}

	private void sendMail(User user, String otp) throws MessagingException {

		MessageModel message = MessageModel.builder().to(user.getEmail()).subject("Verify Your OTP")
				.text("<p>Hii....<br>" + "Thanks for your Intrest in E-Commerce"
						+ "please Verify Your Mail Id Using The OTP Given below.. </p>" + "<br>" + "<h1>" + otp
						+ "</h1>" + "<br>" + "with best regards" + "<h3>E-Commerce</h3>" + "")
				.build();
		mailService.sendMessage(message);
	}

	private String ganerateOTP() {

		return new DecimalFormat("000000").format(new Random().nextInt(999999));
	}

	private <T extends User> T mapToUser(UserRequest userRequest) {

		UserRole role = userRequest.getRole();
		User user = null;
		switch (role) {
		case SELLER -> user = new Seller();
		case CUSTOMER -> user = new Customer();
		default -> throw new RoleNotSpecifyException("role is null");
		}
		String[] split = userRequest.getEmail().split("@");
		user.setDisplayUsername(userRequest.getName());
		user.setUsername(split[0]);
		user.setEmail(userRequest.getEmail());
		user.setPassword(userRequest.getPassword());
		user.setRole(role);
		user.setDeleted(false);
		user.setEmailVerified(false);
		return (T) user;
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(OtpRequest otpRequest) {
		if (otpCache.get(otpRequest.getEmail()) == null)
			throw new OtpExpaireException("otp is Expaire...");
		if (!otpRequest.getOtp().equals(otpCache.get(otpRequest.getEmail())))
			throw new InvalidOTPException("please enter Correct OTP");

		User user = userCache.get(otpRequest.getEmail());
		if (user == null)
			throw new RegistrationSessionExpaireException(" User Registration Session isExpaired ..");

		user.setEmailVerified(true);
		user = userRepo.save(user);
		otpCache.remove(user.getEmail());
		userCache.remove(user.getEmail());

		return ResponseEntity.status(HttpStatus.CREATED.value())
				.body(responseStructure.setStatusCode(HttpStatus.CREATED.value())
						.setMessage("verification is done.user is Register").setData(mappToUserResponse(user)));
	}

	private UserResponse mappToUserResponse(User user) {

		return UserResponse.builder().userId(user.getUserId()).displayName(user.getDisplayUsername())
				.username(user.getUsername()).email(user.getEmail()).isDeleted(user.isDeleted())
				.isEmailVerified(user.isEmailVerified()).role(user.getRole()).build();
	}

}
