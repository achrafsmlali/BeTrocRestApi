package com.betroc.service;

import com.betroc.model.Category;
import com.betroc.repository.CategoryRepository;
import com.betroc.repository.ImageRepository;
import com.betroc.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Value("${server.url}")
    private String urlServer;



    @Override
    public List<Category> getAllCategories(){

        List<Category> categories = this.categoryRepository.findAll();

        //Get all imgName from categries and change them to URL
        for(int i=0; i< categories.size(); i++){
            for(int j=0; j< categories.get(i).getSubCategories().size();j++){

                String uriDownloadImage = " ";
                try{

                    uriDownloadImage = this.urlServer + "/downloadImage/" + this.imageRepository
                                                                            .findByNameStartingWith(this.subCategoryRepository
                                                                            .findByImgNameStartingWith(categories.get(i)
                                                                                    .getSubCategories()
                                                                                    .get(j)
                                                                                    .getimgName()).getimgName()).getName();
                }catch(NullPointerException e){
                    //TODO LGO THIS EXECEPTION
                    System.out.println("Error with ==> " + this.subCategoryRepository
                            .findByImgNameStartingWith(categories.get(i)
                                    .getSubCategories()
                                    .get(j)
                                    .getimgName()).getimgName());
                }
                //Change the imgName Value from text name to url
                categories.get(i)
                        .getSubCategories()
                        .get(j)
                        .setimgName(uriDownloadImage);

            }
        }

        return categories;
    }
}
