package com.betroc.service;

import org.springframework.stereotype.Service;
import com.fasterxml.uuid.Generators;

@Service
public class ImageNameGeneratorServiceImpl implements ImageNameGeneratorService{

    @Override
    public String getNewName(String originalName){

        return originalName + "_" + Generators.timeBasedGenerator().generate().toString();

    }
}
