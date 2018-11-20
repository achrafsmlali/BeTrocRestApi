package com.betroc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * this class automatically bind the propertie image defined in the application.properties file to a POJO class
 */
@ConfigurationProperties(prefix="image")
public class ImageStorageProperties {
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
