package com.api.manager.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.manager.models.CategoryModel;
import com.api.manager.services.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //Route Of Get Category all
    @GetMapping
    public ArrayList<CategoryModel> getCategory(){
        return this.categoryService.getCategory();
    }

    //Route Create a category
    @PostMapping
    public CategoryModel saveCategory(@RequestBody CategoryModel category){
        if(category != null){
            return this.categoryService.saveCategory(category);
        }else{
            return null;
        }
    }

    //Route Get category By Id
    @GetMapping(path ="/{id}")
    public Optional<CategoryModel> getCategoryById(@PathVariable("id") Long id){
        return this.categoryService.getCategoryById(id);
    }
 
    //Route Uptdate category By Id
    @PutMapping(path = "/{id}")
    public CategoryModel updateCategory(@RequestBody CategoryModel request, @PathVariable("id") Long id){
        return this.categoryService.updateCategoryById(request, id);
    }

    //Route Delete Category
    @DeleteMapping(path ="/{id}")
    public String deleteCategoryById(@PathVariable("id") Long id){
        boolean ok = this.categoryService.deleteCategory(id);
        if (ok){
            return "Category deleted with id: " + id;
        }else{
            return "could not delete category with id: " + id;
        }
    }

}
