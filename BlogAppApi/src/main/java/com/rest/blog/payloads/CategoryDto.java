
package com.rest.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto extends RepresentationModel<CategoryDto> {
private Integer categoryid;
@NotBlank
@Size(min=4,message="Minimum length is 4.")
private String categoryTitle;
@NotBlank
@Size(min=10,message="minimum length is 1")
private String categoryDescription;

}
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
