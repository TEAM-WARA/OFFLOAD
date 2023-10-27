package dev.creative.creative.controller;


import dev.creative.creative.service.ImageService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

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

    @ApiOperation(value = "Image Download API", notes = "이미지 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "유저 인덱스 번호", required = true, dataType = "long", example = "1", defaultValue = "")
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=400, message = "값이 없습니다.")
    })
    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public byte[] download(
           @PathVariable("id") long id
    ){
        logger.info("이미지 조회 : " + id);
        return this.imageService.download(id);
    }



}
