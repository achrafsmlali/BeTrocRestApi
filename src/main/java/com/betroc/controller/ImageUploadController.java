package com.betroc.controller;

import com.betroc.exception.BadRequestException;
import com.betroc.model.Image;
import com.betroc.payload.MultipleFilesUploadResponse;
import com.betroc.repository.ImageRepository;
import com.betroc.service.ImageNameGeneratorService;
import com.betroc.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ImageUploadController {

    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired
    private ImageNameGeneratorService imageNameGeneratorService;

    @Autowired
    private ImageRepository imageRepository;

    @PostMapping("/uploadImage")
    public Image uploadImage(@RequestParam("file") MultipartFile imageFile){

        //test if file is an image
        String contentType = imageFile.getContentType();
        if(!contentType.startsWith("image"))
             throw new BadRequestException("the file to upload is not an Image");

        //create new name for the file
        String imageExtension = imageFile.getOriginalFilename().split("\\.")[1];
        String imageName = imageNameGeneratorService.getNewName(imageFile.getOriginalFilename())+"."+imageExtension;

        //store the image
        imageStorageService.storeFile(imageFile,imageName);

        //create an image instance
        Image image = new Image();
        image.setName(imageName);
        Image imgResult = imageRepository.save(image);

        return imgResult;

    }

    @GetMapping("/downloadImage/{fileName:.+}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String fileName, HttpServletRequest request) {
        //Get full name of image from database (we need this to display subCategory images)
        String imageNameFromDataBase = this.imageRepository.findByNameStartingWith(fileName).getName();
        // Load file as Resource
        Resource resource = imageStorageService.loadFileAsResource(imageNameFromDataBase);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            //todo
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/uploadMultipleFiles")
    public MultipleFilesUploadResponse uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {

        List<Image> images = new ArrayList();;
        List<String> filesThatNotImages = new ArrayList();

        //check if it's an image then add it upload it to the server and add its Image object to the Images loaded list
        //else add the file name to the list of nonImageFiles
        //todo if no file in the liste
        for (int i = 0 ;i<files.length;i++){
            try {
                images.add(uploadImage(files[i]));
            }catch (BadRequestException bre){
                filesThatNotImages.add(files[i].getOriginalFilename());
            }
        }

        return new MultipleFilesUploadResponse(images,filesThatNotImages);
    }


}
