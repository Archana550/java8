

package com.rest.blog.services.imple;

import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;

import com.rest.blog.entities.Category;
import com.rest.blog.entities.Post;
import com.rest.blog.entities.User;
import com.rest.blog.exception.ResourceNotFoundException;

import com.rest.blog.payloads.PostDto;

import com.rest.blog.repositories.CategoryRepo;
import com.rest.blog.repositories.PostRepo;
import com.rest.blog.repositories.UserRepo;
import com.rest.blog.services.PostService;

@Service
public class PostServiceImple implements PostService {

@Autowired
private PostRepo postrepo;

@Autowired
private ModelMapper modelmapper;
/**ModelMapper provides a drop-in solution when our source and destination objects are similar to each other.*/

@Autowired
private UserRepo userRepo;

@Autowired
private CategoryRepo categoryRepo;

public static final String CATEGORY="Category";
public static final String CATEGORY_ID="category id";
public static final String POST="Post";
public static final String POST_ID="Post id";


@Override
public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

User user = this.userRepo.findById(userId)
.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

Category category = this.categoryRepo.findById(categoryId)
.orElseThrow(() -> new ResourceNotFoundException(CATEGORY, CATEGORY_ID, categoryId));

Post post = this.modelmapper.map(postDto, Post.class);
//post.setImageName("default.png");
post.setCreatedAt(new Date());
post.setUser(user);
post.setCategory(category);

Post newPost = this.postrepo.save(post);
return this.modelmapper.map(newPost, PostDto.class);
}


@Override
public PostDto updatePost(PostDto postDto, Integer postId) {
Post post = this.postrepo.findById(postId)
.orElseThrow(() -> new ResourceNotFoundException(POST, POST_ID, postId));
post.setTitle(postDto.getTitle());
post.setContent(postDto.getContent());
Post updatedpost = this.postrepo.save(post);
return this.modelmapper.map(updatedpost, PostDto.class);

}

@Override
public Post deletePost(Integer postId) {

Post post = this.postrepo.findById(postId)
.orElseThrow(() -> new ResourceNotFoundException(POST, POST_ID, postId));
Post deletedpost = post;
this.postrepo.delete(post);
return deletedpost;
}

@Override
public List<PostDto> getAllPost(Integer pageNumber, Integer pageSize) {

Page<Post> pagePost = this.postrepo.findAll(PageRequest.of(pageNumber, pageSize));
        //pagePost.getTotalPages();
List<Post> allPosts = pagePost.getContent();
return allPosts.stream().map(post -> this.modelmapper.map(post, PostDto.class))
.collect(Collectors.toList());
}

@Override

public PostDto getPostById(Integer postId) {
Post post = this.postrepo.findById(postId)
.orElseThrow(() -> new ResourceNotFoundException(POST, POST_ID, postId));
return this.modelmapper.map(post, PostDto.class);
}

@Override
public List<PostDto> getPostsByCategory(Integer categoryId) {
Category cat = this.categoryRepo.findById(categoryId)
.orElseThrow(() -> new ResourceNotFoundException(CATEGORY, CATEGORY_ID, categoryId));

List<Post> posts = this.postrepo.findByCategory(cat);

return posts.stream().map(post -> this.modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
}

@Override
public List<PostDto> getPostsByUser(Integer userId) {

User user = this.userRepo.findById(userId)
.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
List<Post> posts = this.postrepo.findByUser(user);
return posts.stream().map(post -> this.modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
}




}
