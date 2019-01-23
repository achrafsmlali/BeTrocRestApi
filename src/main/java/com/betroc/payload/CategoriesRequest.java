package com.betroc.payload;

import com.betroc.model.SubCategory;
import javax.validation.constraints.NotBlank;
import java.util.List;


public class CategoriesRequest {



    @NotBlank
    private String title;

    @NotBlank
    private String description;



    private List<SubCategory> subCategories;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

}
