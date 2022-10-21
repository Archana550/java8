
package com.rest.blog.payloads;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

public class PostList extends RepresentationModel<PostList> {
private static final long serialVersionUID = 1L;

   private List<PostDto> posts = new ArrayList<>();

   public List<PostDto> getPosts() {
       return posts;
   }

   public void setPosts(List<PostDto> posts) {
       this.posts = posts;
   }
}
