
package com.rest.blog.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rest.blog.utils.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;


@Component
public class JwtFilter extends OncePerRequestFilter {

@Autowired
JwtUtil jwtutil;

@Autowired
UserDetailsService uservice;

public static final Logger logged = LoggerFactory.getLogger(JwtFilter.class);

@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
throws ServletException, IOException {

String fulltoken = request.getHeader("Authorization");

String username = null;

String token = null;

if (fulltoken != null && fulltoken.startsWith("Bearer ")) {
token = fulltoken.substring(7);// from 0-based indexing
try {

username = jwtutil.extractUsername(token); // jwt.io
} catch(IllegalArgumentException e) {
logged.info("Unable to get jwt token");
}catch(ExpiredJwtException e) {
logged.info("Jwt token has expired");
}catch(MalformedJwtException e) {
logged.info("Invalid jwt");
}

// validation


} else {
logged.info("Invalid token.");
}

//validation after getting token


if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

UserDetails uds = uservice.loadUserByUsername(username);

if(Boolean.TRUE.equals(jwtutil.validateToken(token, uds))) {
UsernamePasswordAuthenticationToken unamePwdAuthToken = new UsernamePasswordAuthenticationToken(uds,
null, uds.getAuthorities());
unamePwdAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

SecurityContextHolder.getContext().setAuthentication(unamePwdAuthToken);
}else {
logged.info("Invalid jwt token");
}

}else {
logged.info("Username is null or context is not null.");
}


filterChain.doFilter(request, response);

}
}
