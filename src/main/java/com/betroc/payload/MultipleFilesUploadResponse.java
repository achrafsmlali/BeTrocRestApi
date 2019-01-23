package com.betroc.payload;

import com.betroc.model.Image;

import java.util.List;

public class MultipleFilesUploadResponse {
    List<Image> images;
    List<String> filesThatNotImages;

    public MultipleFilesUploadResponse(List<Image> images, List<String> filesThatNotImages) {
        this.images = images;
        this.filesThatNotImages = filesThatNotImages;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<String> getFilesThatNotImages() {
        return filesThatNotImages;
    }

    public void setFilesThatNotImages(List<String> filesThatNotImages) {
        this.filesThatNotImages = filesThatNotImages;
    }
}
