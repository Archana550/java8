
package com.rest.blog.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rest.blog.entities.User;

import com.rest.blog.exception.UserNotFoundException;
import com.rest.blog.repositories.UserRepo;

@Service
public class UserDetailServiceImple implements UserDetailsService {
@Autowired
private UserRepo userrepo;

@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
User user =this.userrepo.findByEmail(username);

if(user == null) {
throw new UserNotFoundException("User not found" +username);
}

return new UserDetailsImple(user);

}

}
