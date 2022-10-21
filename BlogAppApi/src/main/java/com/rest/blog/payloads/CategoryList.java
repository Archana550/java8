
package com.rest.blog.payloads;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

public class CategoryList extends RepresentationModel<CategoryList>{

private static final long serialVersionUID = 1L;

   private List<CategoryDto> categories = new ArrayList<CategoryDto>();

   public List<CategoryDto> getCategories() {
       return categories;
   }

   public void setCategories(List<CategoryDto> categories) {
       this.categories = categories;
   }
}
