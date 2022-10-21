
package com.rest.blog.services;

import java.util.List;

import com.rest.blog.entities.Category;
import com.rest.blog.payloads.CategoryDto;

public interface CategoryService {


//create
CategoryDto createCategory(CategoryDto categoryDto);
//update
CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
//delete
 Category deleteCategory(Integer categoryId);

//get
 CategoryDto getCategory(Integer categoryId);
//getAll
 
 List<CategoryDto> getCategories();
}





