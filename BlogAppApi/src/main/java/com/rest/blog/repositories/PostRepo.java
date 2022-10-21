package com.rest.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.blog.entities.Category;
import com.rest.blog.entities.Post;
import com.rest.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

List<Post> findByUser(User user);

List<Post> findByCategory(Category category);

}






