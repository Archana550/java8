
package com.rest.blog.services;

import java.util.List;

import com.rest.blog.entities.Post;
import com.rest.blog.payloads.PostDto;

public interface PostService {

/** Create post */
PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);


/** Update post */
PostDto updatePost(PostDto postDto, Integer postId);

/** Delete post */
Post deletePost(Integer postId);

/** Get post */
PostDto getPostById(Integer postId);

/** Get post by category */
List<PostDto> getPostsByCategory(Integer categoryId);

/** Get post by user */
List<PostDto> getPostsByUser(Integer userId);

/**Get all post */
List<PostDto> getAllPost(Integer pageNumber, Integer pageSize);

}
