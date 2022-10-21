
package com.rest.blog.payloads;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto extends RepresentationModel<PostDto>{

private Integer postId;
@Size(min=4, message="Title must be 4 characters long.")
@NotBlank(message="Title is mandatory")
private String title;
@Size(min=4, message="Content must be 4 characters long.")
@NotBlank(message="Content is mandatory")
private String content;

private Date createdAt;
private Integer categoryId;
private CategoryDto category;
private UserDto user;

}
