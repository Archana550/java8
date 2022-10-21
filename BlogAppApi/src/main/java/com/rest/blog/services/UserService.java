
package com.rest.blog.services;



import java.util.List;

import com.rest.blog.payloads.UserDto;


public interface UserService {


UserDto updateUser(UserDto user, Integer userId);
UserDto getUserById(Integer userId);
List<UserDto> getAllUsers();
void deleteUser(Integer userId);
}

