package com.betroc.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {
    public void storeFile(MultipartFile file, String imageName);
    public Resource loadFileAsResource(String fileName);
    public void deleteFile(String fileName);

}
