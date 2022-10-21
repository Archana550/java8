
package com.rest.blog.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.blog.entities.User;
import com.rest.blog.payloads.Emailverification;
import com.rest.blog.payloads.UserDto;
import com.rest.blog.repositories.UserRepo;
import com.rest.blog.services.EmailService;

import com.rest.blog.services.UserService;

@RestController
@RequestMapping("/api")
public class EmailController {

@Autowired
private EmailService emailService;
@Autowired
private UserService userService;

@Autowired
private UserRepo userrepo;

/**Admin can send the newsletter*/
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/newsletter/{postId}")
public ResponseEntity<HashMap<String, String>> sendEmail(@PathVariable int postId) {

HashMap<String, String> hmap = new HashMap<>();
ArrayList<String> newsletter = new ArrayList<>();
hmap.put("Response", "Sent successfully");
List<UserDto> users = userService.getAllUsers();
for (UserDto user : users) {
if ((user.getNewsletter() == 1)&&(user.isEnabled())) {
newsletter.add(user.getEmail());

}
}
boolean status = emailService.sendSimpleMail(newsletter, postId);

if (status) {
return ResponseEntity.status(HttpStatus.OK).body(hmap);
}
hmap.put("Response", "Either token is missing or something went wrong.");
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(hmap);

}

/** For the verification of email */

@PostMapping("/email/verify")
public ResponseEntity<HashMap<String, String>> verifyemail(@Valid @RequestBody Emailverification vdetails) {

/**
* finding user from the email to match the otp entered with the otp of database
*/
String useremail = vdetails.getEmail();
User user = this.userrepo.findByEmail(useremail);
HashMap<String, String> hmap = new HashMap<>();

/**Checking the user*/
if (user == null) {

hmap.put("Response", "This email id is not registered.");
return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hmap);
}

/**If user is already verified*/
if (user.isEnabled()) {

hmap.put("Response", "Email Already Verified.");
return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hmap);
}

/**matching the user otp with database*/
if (user.getOtp() == vdetails.getOtp()) {
/**once used we are setting the otp to null */
user.setOtp(0);
/**enabling the user*/
user.setEnabled(true);
userrepo.save(user);

hmap.put("Response", "Email Verified Successfully.");
return ResponseEntity.status(HttpStatus.OK).body(hmap);

}

hmap.put("Response", "Wrong otp entered.");
return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(hmap);

}
}
