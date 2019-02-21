package com.betroc.controller;

import com.betroc.model.Category;
import com.betroc.payload.CategoriesRequest;
import com.betroc.repository.CategoryRepository;
import com.betroc.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/api")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private CategoryRepository categoryRepository;



    @PostMapping("/setCategories")
    public List<Category> getCategories(@Valid @RequestBody ArrayList<CategoriesRequest> categoriesRequest) {
        //list of categories
        List<Category> categories = new ArrayList<>();
        //add the categories list
        for(CategoriesRequest categoryRequest: categoriesRequest) {
            Category category = new Category();
            category.setDescription(categoryRequest.getDescription());
            category.setTitle(categoryRequest.getTitle());
            category.setSubCategories(categoryRequest.getSubCategories());

            categories.add(category);
        }
        this.categoryRepository.saveAll(categories);
        return categories;
    }


    @GetMapping("/getCategories")
    public List<Category> getCategories() {

        List<Category> categoryList = this.categoriesService.getAllCategories();

        return categoryList;
    }



}
