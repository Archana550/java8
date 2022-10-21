
package com.rest.blog.services;

import java.util.ArrayList;

import com.rest.blog.entities.User;

public interface EmailService {

boolean sendSimpleMail(ArrayList<String> newsletter, int postId);

int sendOTPforEmailVerification(User user);
}


