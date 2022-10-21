
package com.rest.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.rest.blog.auth.JwtAuthEntryPoint;
import com.rest.blog.auth.JwtFilter;
import com.rest.blog.auth.UserDetailServiceImple;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
@Autowired
private UserDetailServiceImple userds;
@Autowired
private JwtFilter jwtfilter;

@Autowired
private JwtAuthEntryPoint jwtAuthEntryPoint;

@Override
protected void configure(HttpSecurity http) throws Exception {



http.csrf().disable()
.authorizeHttpRequests()
.antMatchers("/api/auth/**").permitAll()
.antMatchers("/api/email/verify/**").permitAll()
//.antMatchers(HttpMethod.GET,readroutes).permitAll()
//.antMatchers("/sharePost/**").permitAll()
.antMatchers("/v3/api-docs").permitAll()
.antMatchers("/v2/api-docs").permitAll()
.antMatchers("/swagger-resources/**").permitAll()
.antMatchers("/swagger-ui/**").permitAll()
.antMatchers("/webjars/**").permitAll()
.anyRequest()
.authenticated()
.and()
.exceptionHandling()
.authenticationEntryPoint(jwtAuthEntryPoint).and().sessionManagement()
.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

http.addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);

}

@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
auth.userDetailsService(userds).passwordEncoder(pwdEncoder());
}

@Bean
public BCryptPasswordEncoder pwdEncoder() {

return new BCryptPasswordEncoder(10);
}

@Bean
@Override
public AuthenticationManager authenticationManagerBean() throws Exception {
return super.authenticationManagerBean();
}
}
