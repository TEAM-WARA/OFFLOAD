package dev.creative.creative.controller;


import dev.creative.creative.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
    private final ImageService imageService;

    public ImageController(
            @Autowired ImageService imageService
    ){
        this.imageService = imageService;
    }


    @PostMapping
    public List<Long> upload(
            @RequestPart("images") List<MultipartFile> images
    ) throws IOException {
        return this.imageService.upload(images);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public byte[] download(
            @PathVariable("id") long id
    ){
        return this.imageService.download(id);
    }



}
