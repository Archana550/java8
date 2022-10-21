

package com.rest.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {


private int id;
@NotNull(message="Name can't be empty.")
@NotBlank(message="Name is mandatory.")
@Size(min=4, message="Name must be 4 characters long.")
private String name;
@Email(message="Email address is not valid!")
private String email;
@NotBlank
@Size(min=3, max = 10,message="Password must be at least 3 chars and maximum 10 chars.")
private String password;
@NotBlank
private String about;
private int newsletter;
private boolean enabled;
private RoleDto role;
}

