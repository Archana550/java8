
package com.rest.blog.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rest.blog.config.AppConstants;

import com.rest.blog.entities.Post;
import com.rest.blog.entities.User;

import com.rest.blog.payloads.PostDto;
import com.rest.blog.payloads.PostList;
import com.rest.blog.repositories.UserRepo;
import com.rest.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

@Autowired
private PostService postService;

@Autowired
private UserRepo userrepo;

/** Create new post */
@PreAuthorize("hasRole('ADMIN')")
@PostMapping("/category/{categoryId}/posts2")
public ResponseEntity<EntityModel<PostDto>> createPost2(@Valid @RequestBody PostDto postdto,
@PathVariable Integer categoryId) {

Authentication auth = SecurityContextHolder.getContext().getAuthentication();
UserDetails prnicipal = (UserDetails) auth.getPrincipal();
String useremail = prnicipal.getUsername();

User user = this.userrepo.findByEmail(useremail);

int userid = user.getId();
PostDto postdto1 = new PostDto();
postdto.setCategoryId(categoryId);
postdto.setTitle(postdto.getTitle());
postdto.setContent(postdto.getContent());

PostDto createdPost = this.postService.createPost(postdto, userid, categoryId);
EntityModel<PostDto> entityModel = EntityModel.of(createdPost);
Link link = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getAllPost(0, 2)).withRel("get-all-posts");

entityModel.add(link);
return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
}

/** Update post */
@PreAuthorize("hasRole('ADMIN')")
@PutMapping("/posts/{postId}")
public ResponseEntity<EntityModel<PostDto>> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {

PostDto updatePost = this.postService.updatePost(postDto, postId);

/** Hateoas links */
EntityModel<PostDto> entityModel = EntityModel.of(updatePost);
Link link = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getAllPost(0, 2)).withRel("all-posts");

entityModel.add(link);

return new ResponseEntity<>(entityModel, HttpStatus.OK);

}

/** Get post by user */
@GetMapping("/user/{userId}/posts")
public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
List<PostDto> posts = this.postService.getPostsByUser(userId);
return new ResponseEntity<>(posts, HttpStatus.OK);
}

/** Get post by category */
@GetMapping("/category/{categoryId}/posts")
public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
return new ResponseEntity<>(posts, HttpStatus.OK);
}

/**
* Get all posts, includes pagination PaginationAndSortingRepository Pagable
* Object
*/
@GetMapping("/posts")
public ResponseEntity<PostList/* PostResponse */> getAllPost(
@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize

) {

PostList postlist = new PostList();
for (PostDto postdto : postService.getAllPost(pageNumber, pageSize)) {
Link link1 = WebMvcLinkBuilder.linkTo(methodOn(EmailController.class).sendEmail(postdto.getPostId()))
.withRel("share-this-post.");

Link link2 = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).updatePost(postdto, postdto.getPostId()))
.withRel("update-this-post.");

Link link3 = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).deletePost(postdto.getPostId()))
.withRel("delete-this-post.");

postdto.add(link1);
postdto.add(link2);
postdto.add(link3);

postlist.getPosts().add(postdto);

}

return new ResponseEntity<>(postlist, HttpStatus.OK);

}

/** Get post by id */
@GetMapping("/posts/{postId}")
public ResponseEntity<EntityModel<PostDto>> getPostById(@PathVariable Integer postId) {
PostDto post = this.postService.getPostById(postId);
/** Hateoas links */
EntityModel<PostDto> entityModel = EntityModel.of(post);
Link link = WebMvcLinkBuilder.linkTo(methodOn(EmailController.class).sendEmail(post.getPostId()))
.withRel("share-post");

entityModel.add(link);

return new ResponseEntity<>(entityModel, HttpStatus.OK);

}

/** Delete post */
@PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/posts/{postId}")
public ResponseEntity<EntityModel<Post>> deletePost(@PathVariable Integer postId) {
Post deletedpost = this.postService.deletePost(postId);
EntityModel<Post> entityModel = EntityModel.of(deletedpost);
Link link = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getAllPost(0, 2)).withRel("all-posts");

entityModel.add(link);

return new ResponseEntity<>(entityModel, HttpStatus.OK);

}

}
