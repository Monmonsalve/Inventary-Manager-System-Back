package com.api.manager.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.manager.models.CategoryModel;
import com.api.manager.repositories.ICategoryRepository;

@Service
public class CategoryService {
    
    @Autowired
    ICategoryRepository categoryRepository;

    //Get all category
    public ArrayList<CategoryModel> getCategory(){
        return (ArrayList<CategoryModel>) categoryRepository.findAll();
    }

    //Save a new category
    public CategoryModel saveCategory(CategoryModel category){
        return categoryRepository.save(category);
    }

    //Get a category By Id
    public Optional<CategoryModel> getCategoryById(Long id){
        return categoryRepository.findById(id);
    }

    //Update a category By Id
    public CategoryModel updateCategoryById(CategoryModel request, Long id){
        CategoryModel category = categoryRepository.findById(id).get();

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return category;
    }

    //Delete a category By Id
    public Boolean deleteCategory(Long id){
        try{
            categoryRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
