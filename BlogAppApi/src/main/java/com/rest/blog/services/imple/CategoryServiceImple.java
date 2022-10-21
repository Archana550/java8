
package com.rest.blog.services.imple;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.blog.entities.Category;
import com.rest.blog.exception.ResourceNotFoundException;
import com.rest.blog.payloads.CategoryDto;
import com.rest.blog.repositories.CategoryRepo;
import com.rest.blog.services.CategoryService;

@Service
public class CategoryServiceImple implements CategoryService {
   
@Autowired
private CategoryRepo categoryRepo;
@Autowired
private ModelMapper modelMapper;
/**ModelMapper provides a drop-in solution when our source and destination objects are similar to each other.*/


public static final String CATEGORY="Category";
public static final String CATEGORY_ID="category id";

@Override
public CategoryDto createCategory(CategoryDto categoryDto) {
Category cat = this.modelMapper.map(categoryDto,  Category.class);
Category addedCat = this.categoryRepo.save(cat);
return this.modelMapper.map(addedCat,  CategoryDto.class);
}

@Override
public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId)
        .orElseThrow(()->new ResourceNotFoundException(CATEGORY,CATEGORY_ID,categoryId));
cat.setCategoryTitle(categoryDto.getCategoryTitle());
cat.setCategoryDescription(categoryDto.getCategoryDescription());
Category updatedCat = this.categoryRepo.save(cat);
return this.modelMapper.map(updatedCat, CategoryDto.class);

}

@Override
public Category deleteCategory(Integer categoryId) {
      Category cat = this.categoryRepo.findById(categoryId)
     .orElseThrow(()->new ResourceNotFoundException(CATEGORY,CATEGORY_ID,categoryId));
     
      Category deletedcategory = cat;
      this.categoryRepo.delete(cat);
     
return deletedcategory;
}

@Override
public CategoryDto getCategory(Integer categoryId) {
Category cat = this.categoryRepo.findById(categoryId)
     .orElseThrow(()->new ResourceNotFoundException(CATEGORY,CATEGORY_ID,categoryId));
     
return this.modelMapper.map(cat, CategoryDto.class);
}

@Override
public List<CategoryDto> getCategories() {
List<Category> categories = this.categoryRepo.findAll();
return categories.stream().map(cat->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
}



}


