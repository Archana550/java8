
package com.rest.blog;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.rest.blog.repositories.RoleRepo;


@SpringBootApplication
@EnableCaching
public class BlogAppApiApplication
/*implements CommandLineRunner */{

@Autowired
BCryptPasswordEncoder bcrypt;
@Autowired
private RoleRepo rolerepo;

public static void main(String[] args) {
SpringApplication.run(BlogAppApiApplication.class, args);
}

@Bean
public ModelMapper modelMapper() {
return new ModelMapper();
}

/*@Override
public void run(String... args) throws Exception {

try {
Role admin =new Role();
admin.setId(AppConstants.ADMIN);
admin.setName("ROLE_ADMIN");


Role user =new Role();
user.setId(AppConstants.NORMAL_USER);
user.setName("ROLE_NORMAL");


this.rolerepo.save(admin);
this.rolerepo.save(user);


}catch(Exception e) {
e.printStackTrace();
}
}
*/




}


