package com.retail.ecommerce.serviceimpl;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.retail.ecommerce.cache.CacheStore;
import com.retail.ecommerce.entity.Accesstoken;
import com.retail.ecommerce.entity.Customer;
import com.retail.ecommerce.entity.RefreshToken;
import com.retail.ecommerce.entity.Seller;
import com.retail.ecommerce.entity.User;
import com.retail.ecommerce.enums.UserRole;
import com.retail.ecommerce.exception.AccessTokenExpireException;
import com.retail.ecommerce.exception.EmailAllreadyPresentException;
import com.retail.ecommerce.exception.InvalidCreadentials;
import com.retail.ecommerce.exception.InvalidEmailException;
import com.retail.ecommerce.exception.InvalidOTPException;
import com.retail.ecommerce.exception.OtpExpaireException;
import com.retail.ecommerce.exception.PleaseGiveRefreshAccessTokenRequest;
import com.retail.ecommerce.exception.RegistrationSessionExpaireException;
import com.retail.ecommerce.exception.RoleNotSpecifyException;
import com.retail.ecommerce.exception.UserIsAllreadyLoginException;
import com.retail.ecommerce.exception.UserIsNotLoginException;
import com.retail.ecommerce.jwt.JwtService;
import com.retail.ecommerce.mailservice.MailService;
import com.retail.ecommerce.mailservice.MessageModel;
import com.retail.ecommerce.repository.AccessTokenRepo;
import com.retail.ecommerce.repository.RefreshTokenRepo;
import com.retail.ecommerce.repository.UserRegisterRepoository;
import com.retail.ecommerce.requestdto.AuthRequest;
import com.retail.ecommerce.requestdto.UserRequest;
import com.retail.ecommerce.responsedto.AuthResponse;
import com.retail.ecommerce.responsedto.OtpRequest;
import com.retail.ecommerce.responsedto.UserResponse;
import com.retail.ecommerce.service.AuthService;
import com.retail.ecommerce.util.ResponseStructure;
import com.retail.ecommerce.util.SimpleResponseStructure;

import jakarta.mail.MessagingException;

@Service
public class AuthServiceImpl implements AuthService {

	private UserRegisterRepoository userRepo;
	private CacheStore<String> otpCache;
	private CacheStore<User> userCache;
	private ResponseStructure<UserResponse> responseStructure;
	private SimpleResponseStructure otpStructure;
	private MailService mailService;
	private PasswordEncoder encoder;
	private AccessTokenRepo accessTokenRepo;
	private RefreshTokenRepo refreshTokenRepo;
	private AuthenticationManager authenticationManager;
	private JwtService jwtService;
	@Value("${myapp.jwt.access.expairation}")
	private long accessExpairation;
	@Value("${myapp.jwt.refresh.expairation}")
	private long refreshExpairation;
	private ResponseStructure<AuthResponse> authStucture;
	private SimpleResponseStructure simpleResponseStructure;

	public AuthServiceImpl(UserRegisterRepoository userRepo, CacheStore<String> otpCache, CacheStore<User> userCache,
			ResponseStructure<UserResponse> responseStructure, SimpleResponseStructure otpStructure,
			MailService mailService, PasswordEncoder encoder, AccessTokenRepo accessTokenRepo,
			RefreshTokenRepo refreshTokenRepo, AuthenticationManager authenticationManager, JwtService jwtService,
			ResponseStructure<AuthResponse> authStucture, SimpleResponseStructure simpleResponseStructure) {
		super();
		this.userRepo = userRepo;
		this.otpCache = otpCache;
		this.userCache = userCache;
		this.responseStructure = responseStructure;
		this.otpStructure = otpStructure;
		this.mailService = mailService;
		this.encoder = encoder;
		this.accessTokenRepo = accessTokenRepo;
		this.refreshTokenRepo = refreshTokenRepo;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.authStucture = authStucture;
		this.simpleResponseStructure = simpleResponseStructure;

	}

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
		user.setDisplayUsername(userRequest.getUsername());
		user.setUsername(split[0]);
		user.setEmail(userRequest.getEmail());
		user.setPassword(encoder.encode(userRequest.getPassword()));
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

	@Override
	public ResponseEntity<ResponseStructure<AuthResponse>> login(AuthRequest authRequest, String accessToken,
			String refreshToken) {

		if (accessToken != null && refreshToken != null)
			throw new UserIsAllreadyLoginException("You Allready Login....");
		// || !accessTokenRepo.existsByToken(accessToken)
		if (accessToken == null && refreshToken != null)
			throw new AccessTokenExpireException("your Accestone expire please Regenerate Your AccessToken");
		String username = authRequest.getEmail().split("@")[0];
		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, authRequest.getPassword()));
		if (!authenticate.isAuthenticated())
			throw new InvalidCreadentials("please enter valid Credentials....");
		SecurityContextHolder.getContext().setAuthentication(authenticate);

		HttpHeaders headers = new HttpHeaders();
		userRepo.findByUsername(username).ifPresent(user -> {
			Accesstoken at = createAccessToken(user, headers);
			RefreshToken rt = createRefreshToken(user, headers);
			at.setUser(user);
			rt.setUser(user);
			at = accessTokenRepo.save(at);
			rt = refreshTokenRepo.save(rt);
		});

		return ResponseEntity.ok().headers(headers)
				.body(authStucture.setStatusCode(HttpStatus.OK.value()).setMessage("succefully login")
						.setData(mapToAuthResponse(username, accessExpairation, refreshExpairation)));
	}

	private AuthResponse mapToAuthResponse(String username, long accessExpairation, long refreshExpairation) {

		User user = userRepo.findByUsername(username).get();

		return AuthResponse.builder().userId(user.getUserId()).username(user.getUsername()).userRole(user.getRole())
				.accessExpiration(accessExpairation).refreshExpiration(refreshExpairation).build();
	}

	private Accesstoken createAccessToken(User user, HttpHeaders headers) {

		String token = jwtService.generateAccessToken(user.getUsername(), user.getRole().name());
		headers.add(HttpHeaders.SET_COOKIE, configureCookie("at", token, accessExpairation));

		return Accesstoken.builder().token(token).expiration(LocalDateTime.now().plusHours(1)).isBlocked(false).build();
	}

	private RefreshToken createRefreshToken(User user, HttpHeaders headers) {

		String token = jwtService.generateRefreshToken(user.getUsername(), user.getRole().name());
		headers.add(HttpHeaders.SET_COOKIE, configureCookie("rt", token, refreshExpairation));

		return RefreshToken.builder().token(token).expiration(LocalDateTime.now().plusDays(15)).isBlocked(false)
				.build();

	}

	private String configureCookie(String name, String value, long maxAge) {
		return ResponseCookie.from(name, value).domain("localhost").path("/").httpOnly(true).secure(false)
				.maxAge(Duration.ofMillis(maxAge)).sameSite("Lax").build().toString();
	}

	@Override
	public ResponseEntity<SimpleResponseStructure> logout(String accessToken, String refreshToken) {

		if (accessToken == null && refreshToken == null)
			throw new UserIsNotLoginException("user is not Login");

		if (refreshToken == null)
			throw new UserIsNotLoginException("user is not Login");
		HttpHeaders headers = new HttpHeaders();

		if (accessToken == null)
			throw new PleaseGiveRefreshAccessTokenRequest("give refresh AccessToken Request");
		refreshTokenRepo.findByToken(refreshToken).ifPresent(refresh -> {
			accessTokenRepo.findByToken(accessToken).ifPresent(access -> {

				refresh.setBlocked(true);
				refreshTokenRepo.save(refresh);
				access.setBlocked(true);
				accessTokenRepo.save(access);
			});
		});

		removeAccess("at", headers);
		removeAccess("rt", headers);

		return ResponseEntity.ok().headers(headers)
				.body(simpleResponseStructure.setMessage("LogOut Sucessfully...").setStatusCode(HttpStatus.OK.value()));

	}

	private void removeAccess(String value, HttpHeaders headers) {
		headers.add(HttpHeaders.SET_COOKIE, removeCookie(value));
	}

	private String removeCookie(String name) {
		return ResponseCookie.from(name, "").domain("localhost").path("/").httpOnly(true).secure(false).maxAge(0)
				.sameSite("Lax").build().toString();
	}

	@Override
	public ResponseEntity<ResponseStructure<AuthResponse>> refreshToken(String accessToken, String refreshToken) {

		if (accessToken != null) {
			accessTokenRepo.findByToken(accessToken).ifPresent(at -> {

				at.setBlocked(true);
				accessTokenRepo.save(at);
			});
		}
		HttpHeaders headers = new HttpHeaders();
		if (refreshToken == null)
			throw new UserIsNotLoginException("user is not login");
		// check if the token is blocked.
		if (refreshTokenRepo.existsByTokenAndIsBlocked(refreshToken, true))
			throw new UserIsNotLoginException("User Is Not LogedIn...");
		// extract issuedAt from rt
		return refreshTokenRepo.findByToken(refreshToken).map(refresh -> {
			System.out.println("extracting credentials");
			Date date = jwtService.getDate(refresh.getToken());
			LocalDateTime.now();
			if (date.getDate() == new Date().getDate()) {
				Accesstoken at = createAccessToken(refresh.getUser(), headers);
				at.setUser(refresh.getUser());
				accessTokenRepo.save(at);
				RefreshToken rt = refreshTokenRepo.findByToken(refreshToken).get();
				headers.add(HttpHeaders.SET_COOKIE, configureCookie("rt", rt.getToken(), refreshExpairation));

			} else {
				refresh.setBlocked(true);
				refreshTokenRepo.save(refresh);
				Accesstoken at = createAccessToken(refresh.getUser(), headers);
				RefreshToken rt = createRefreshToken(refresh.getUser(), headers);
				at.setUser(refresh.getUser());
				rt.setUser(refresh.getUser());
				accessTokenRepo.save(at);
				refreshTokenRepo.save(rt);
			}
			return ResponseEntity.ok().headers(headers)
					.body(authStucture.setStatusCode(HttpStatus.OK.value()).setMessage("token is generated..").setData(
							mapToAuthResponse(refresh.getUser().getUsername(), accessExpairation, refreshExpairation)));

		}).orElseThrow(() -> new UserIsNotLoginException("user is not Login..."));
	}

}
