
package com.rest.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Emailverification {
@NotNull(message="Email can't be empty.")
@Email
private String email;
@NotNull
private int otp;
}
