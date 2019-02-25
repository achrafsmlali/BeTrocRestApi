package com.betroc.listener;

import com.betroc.model.Image;
import com.betroc.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PreRemove;

@Component
public class EnityImageListener {

    private static ImageStorageService imageStorageService;

    @Autowired
    public void init(ImageStorageService imageStorageService) {
        EnityImageListener.imageStorageService = imageStorageService;
    }

    @PreRemove
    public void DeleteImageFromServer(Image image){
        imageStorageService.deleteFile(image.getName());
    }

}
