
package com.rest.blog.controllers;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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
import org.springframework.web.bind.annotation.RestController;

import com.rest.blog.auth.UserDetailServiceImple;
import com.rest.blog.entities.Category;
import com.rest.blog.entities.User;

import com.rest.blog.payloads.CategoryDto;
import com.rest.blog.payloads.CategoryList;
import com.rest.blog.repositories.UserRepo;
import com.rest.blog.services.CategoryService;
import com.rest.blog.services.UserService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

public static final String CATEGORY = "Category";
public static final String CATEGORY_ID = "Category ID";


@Autowired
private CategoryService categoryService;
@Autowired
UserDetailServiceImple uservice;
@Autowired
private UserService userService;

@Autowired
private UserRepo userrepo;

/**Create a category*/
@PreAuthorize("hasRole('ADMIN')")
@PostMapping("/")
public ResponseEntity<EntityModel<CategoryDto>> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
CategoryDto createCategory = this.categoryService.createCategory(categoryDto);

EntityModel<CategoryDto> entityModel = EntityModel.of(createCategory);
Link link = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getCategories()).withRel("all-categories");

entityModel.add(link);

return new ResponseEntity<>(entityModel, HttpStatus.OK);

}

/**Update a category*/
@PreAuthorize("hasRole('ADMIN')")
@PutMapping("/{categoryId}")
public ResponseEntity<EntityModel<CategoryDto>> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
@PathVariable Integer categoryId) {
CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, categoryId);

EntityModel<CategoryDto> entityModel = EntityModel.of(updatedCategory);
Link link = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getCategories()).withRel("all-categories");

entityModel.add(link);

return new ResponseEntity<>(entityModel, HttpStatus.OK);


}

/**Delete a category*/
@PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/{categoryId}")
public ResponseEntity<EntityModel<Category>> deleteCategory(@PathVariable Integer categoryId) {
Category deletedcategory = this.categoryService.deleteCategory(categoryId);

EntityModel<Category> entityModel = EntityModel.of(deletedcategory);
Link link = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getCategories()).withRel("all-categories");

entityModel.add(link);

return new ResponseEntity<>(entityModel, HttpStatus.OK);

}

/**Get a category*/
@GetMapping("/{categoryId}")
public ResponseEntity<EntityModel<CategoryDto>> getCategory(@PathVariable Integer categoryId) {
CategoryDto updatedCategory = this.categoryService.getCategory(categoryId);

EntityModel<CategoryDto> entityModel = EntityModel.of(updatedCategory);
Link link = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getCategories()).withRel("all-categories");

entityModel.add(link);

return new ResponseEntity<>(entityModel, HttpStatus.OK);


}

/**Get all categories*/
@GetMapping("/")
public ResponseEntity<CategoryList> getCategories() {
/** current user from token */
Authentication auth = SecurityContextHolder.getContext().getAuthentication();
UserDetails prnicipal = (UserDetails) auth.getPrincipal();
String useremail = prnicipal.getUsername();

User user = this.userrepo.findByEmail(useremail);
/**Hateoas links*/
CategoryList categorylist = new CategoryList();
for (CategoryDto category : categoryService.getCategories()) {
Link link1 = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getCategory(category.getCategoryid()))
.withRel("Get-this-category");

Link link2 = WebMvcLinkBuilder
.linkTo(methodOn(this.getClass()).updateCategory(category, category.getCategoryid()))
.withRel("Update-this-category");

Link link3 = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).deleteCategory(category.getCategoryid()))
.withRel("Delete-this-category");

Link link4 = WebMvcLinkBuilder
.linkTo(methodOn(PostController.class).createPost2(null, category.getCategoryid()))
.withRel("Add-post-to-this-category");

category.add(link1);
category.add(link2);
category.add(link3);
category.add(link4);

categorylist.getCategories().add(category);
}


return new ResponseEntity<>(categorylist, HttpStatus.OK);
}

}

