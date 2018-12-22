package com.betroc.service;

import com.betroc.config.ImageStorageProperties;
import com.betroc.exception.FileStorageException;
import com.betroc.exception.MyFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;

@Service
@Transactional
public class ImageStorageServiceImpl implements ImageStorageService{

    private final Path imageStorageLocation;

    @Autowired
    public ImageStorageServiceImpl(ImageStorageProperties imageStorageProperties) {
        this.imageStorageLocation = Paths.get(imageStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.imageStorageLocation);
        } catch (IOException ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public void storeFile(MultipartFile file, String imageName){


        try {

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.imageStorageLocation.resolve(imageName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + imageName + ". Please try again!", ex);
        }

    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.imageStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    @Override
    public void deleteFile(String fileName) {

        Path filePath = this.imageStorageLocation.resolve(fileName).normalize();
        File file = new File(filePath.toString());

        if(file.delete())
        {
            //TODO log
        }
        else
        {
            //TODO log
        }

    }
}
