
package com.rest.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.rest.blog.entities.User;
import com.rest.blog.exception.AccessNotPermitedException;
import com.rest.blog.payloads.ApiResponse;

import com.rest.blog.payloads.UserDto;
import com.rest.blog.repositories.UserRepo;
import com.rest.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

@Autowired
private UserService userService;


@Autowired
private UserRepo userrepo;
/**Update user*/
@PutMapping("/{userId}")
public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable int userId) {
/** current user from token */
Authentication auth = SecurityContextHolder.getContext().getAuthentication();
UserDetails prnicipal = (UserDetails) auth.getPrincipal();
String useremail = prnicipal.getUsername();

User user = this.userrepo.findByEmail(useremail);

if(user.getId()!=userId) {
throw new AccessNotPermitedException("You can't update other user's profile");
}

UserDto updatedUser = this.userService.updateUser(userDto, userId);
return ResponseEntity.ok(updatedUser);
}


/**Delete user*/
@PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/{userId}")
public ResponseEntity<ApiResponse> deleteUser(@PathVariable int userId) {
this.userService.deleteUser(userId);

return new ResponseEntity<>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
}

/** get all users */
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/")
public ResponseEntity<List<UserDto>> getAllUsers() {
return ResponseEntity.ok(this.userService.getAllUsers());
}


/**Get a user*/
@GetMapping("/{userId}")
public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
return ResponseEntity.ok(this.userService.getUserById(userId));
}

}
