
package com.rest.blog.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.blog.auth.JwtAuthRequest;
import com.rest.blog.auth.JwtAuthResponse;
import com.rest.blog.auth.UserDetailServiceImple;
import com.rest.blog.config.AppConstants;
import com.rest.blog.entities.Role;
import com.rest.blog.entities.User;
import com.rest.blog.exception.ApiException;
import com.rest.blog.exception.EmailAlreadyRegisteredException;

import com.rest.blog.payloads.UserDto;
import com.rest.blog.repositories.RoleRepo;
import com.rest.blog.repositories.UserRepo;
import com.rest.blog.services.EmailService;

import com.rest.blog.utils.JwtUtil;



@RestController
@RequestMapping("/api/auth")
public class JwtAuthController {

// logger
public static final Logger logger = LoggerFactory.getLogger(JwtAuthController.class);

@Autowired
JwtUtil jutil;

@Autowired
private UserRepo userRepo;

@Autowired
private ModelMapper modelMapper;

@Autowired
UserDetailServiceImple uservice;

@Autowired
AuthenticationManager authmanager;

@Autowired
private UserRepo userrepo;
@Autowired
private RoleRepo rolerepo;
@Autowired
private EmailService emailService;

@Autowired
BCryptPasswordEncoder bcrypt;

/**Login*/
@PostMapping("/login")
public ResponseEntity<EntityModel<JwtAuthResponse>> createToken(@Valid @RequestBody JwtAuthRequest request)
{

try {

authmanager.authenticate(
new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

} catch (BadCredentialsException e) {
logger.info("Invalid details");
throw new ApiException("Bad Credentials");
}

/**Finding user by email id*/
UserDetails udservice = uservice.loadUserByUsername(request.getUsername());
User loggedinuser = userRepo.findByEmail(udservice.getUsername());


/** JWT token generation */
String token = jutil.generateToken(udservice);
JwtAuthResponse tok = new JwtAuthResponse(token);

/** Hateoas links */
EntityModel<JwtAuthResponse> entityModel = EntityModel.of(tok);
Link link1 = WebMvcLinkBuilder.linkTo(methodOn(PostController.class).getAllPost(0, 2)).withRel("See-all-posts");

Link link2 = WebMvcLinkBuilder.linkTo(methodOn(CategoryController.class).getCategories())
.withRel("See-all-category");

entityModel.add(link1);
entityModel.add(link2);

return new ResponseEntity<>(entityModel, HttpStatus.OK);

}

/**Registration*/
@PostMapping("/register")
public ResponseEntity<EntityModel<UserDto>> registerUser(@Valid @RequestBody UserDto userDto){

User user = modelMapper.map(userDto, User.class);

/** Email Alreday registered check */
if (userRepo.findByEmail(user.getEmail()) != null) {
throw new EmailAlreadyRegisteredException("Email already registered.");
}
/** If no role is passed, assign normal. */
if (userDto.getRole() == null) {

Role role = rolerepo.findById(AppConstants.NORMAL_USER).get();
user.setRole(role);
}

/**Encrypting the password*/
user.setPassword(bcrypt.encode(user.getPassword()));


/** user will be enabled upon verification */
user.setEnabled(false);

        /**Sending otp to users mail*/
int otp = emailService.sendOTPforEmailVerification(user);

        /**setting otp to user to be saved in db*/
user.setOtp(otp);

        /**saving user in db*/
User newUser = userRepo.save(user);

UserDto registeredUser = modelMapper.map(newUser, UserDto.class);
/** Hateoas links */
EntityModel<UserDto> entityModel = EntityModel.of(registeredUser);
Link link = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).createToken(null)).withRel("Login");

entityModel.add(link);

return new ResponseEntity<>(entityModel, HttpStatus.CREATED);

}

}
