
package com.rest.blog.services.imple;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;

import com.rest.blog.entities.Post;
import com.rest.blog.entities.User;
import com.rest.blog.exception.ResourceNotFoundException;

import com.rest.blog.repositories.PostRepo;
import com.rest.blog.services.EmailService;

import com.rest.blog.utils.JwtUtil;

@Service
public class EmailServiceImple implements EmailService {

@Autowired
private JavaMailSender javaMailSender;

@Autowired
static JwtUtil jutil;

@Autowired
private PostRepo postrepo;

@Value("${spring.mail.username}")
private static String sender;

@Override
public boolean sendSimpleMail(ArrayList<String> newsletter, int postId) {

try {

SimpleMailMessage mailMessage = new SimpleMailMessage();
mailMessage.setFrom(sender);

Authentication auth = SecurityContextHolder.getContext().getAuthentication();
UserDetails pricipal = (UserDetails) auth.getPrincipal();
String useremail = pricipal.getUsername();
           

Post post = this.postrepo.findById(postId)
.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
mailMessage.setTo(useremail);
mailMessage.setText(post.getContent());
mailMessage.setSubject(post.getTitle());


int i=0;
int size = newsletter.size();
while(size>0) {

mailMessage.setTo(newsletter.get(i));
javaMailSender.send(mailMessage);
i++;
size--;
}

return true;

} catch (Exception e) {

return false;
}
}


@Override
public int sendOTPforEmailVerification(User user) {

try {

SimpleMailMessage verificationotp = new SimpleMailMessage();
verificationotp.setFrom(sender);

String useremail = user.getEmail();
           
Random r=new Random(System.currentTimeMillis());
       int otp=((1+r.nextInt(2))*10000+r.nextInt(10000));
//System.out.println(otp);
verificationotp.setTo(useremail);
//System.out.println("otp sent====before==============");
verificationotp.setSubject("Verification OTP ");
verificationotp.setText("Your OTP for email verification is : "+otp);  
javaMailSender.send(verificationotp);
//System.out.println("otp sent==================");
return otp;

} catch (Exception e) {
//System.out.println("otp catch==================");
e.printStackTrace();
return 0;
}

}






}


