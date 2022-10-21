

package com.rest.blog.services.imple;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rest.blog.entities.User;
import com.rest.blog.exception.ResourceNotFoundException;

import com.rest.blog.payloads.UserDto;

import com.rest.blog.repositories.UserRepo;
import com.rest.blog.services.UserService;

@Service
public class UserServiceImple implements UserService {

@Autowired
private UserRepo userRepo;

@Autowired
BCryptPasswordEncoder bcrypt;

@Autowired
private ModelMapper modelMapper;
/**ModelMapper provides a drop-in solution when our source and destination objects are similar to each other.*/

@Override
public UserDto updateUser(UserDto userDto, Integer userId) {
User user = this.userRepo.findById(userId)
.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

user.setName(userDto.getName());
user.setEmail(userDto.getEmail());
user.setPassword(bcrypt.encode(userDto.getPassword()));

user.setAbout(userDto.getAbout());

User updatedUser = this.userRepo.save(user);
return this.userToDto(updatedUser);
}

@Override
public UserDto getUserById(Integer userId) {
User user = this.userRepo.findById(userId)
.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
return this.userToDto(user);
}

@Override
public List<UserDto> getAllUsers() {
List<User> users = this.userRepo.findAll();

return users.stream().map(this::userToDto).collect(Collectors.toList());
}




@Override
public void deleteUser(Integer userId) {
User user = this.userRepo.findById(userId)
.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
this.userRepo.delete(user);
}

private UserDto userToDto(User user) {
return this.modelMapper.map(user, UserDto.class);

}



}
