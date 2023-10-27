package dev.creative.creative.service.impl;

import dev.creative.creative.entity.ImageEntity;
import dev.creative.creative.repository.ImageRepository;
import dev.creative.creative.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// 이미지 관련 서비스 구현체
@Service
public class ImageServiceImpl implements ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
    private final ImageRepository imageRepository;

    public ImageServiceImpl(
            @Autowired ImageRepository imageRepository
    ){
        this.imageRepository = imageRepository;
    }

    @Override
    public List<Long> upload(List<MultipartFile> images) throws IOException {
        List<Long> id = new ArrayList<>();
        for(MultipartFile image : images){
            ImageEntity imageEntity = ImageEntity.builder().
                    image(image.getBytes())
                    .build();
            imageEntity = this.imageRepository.save(imageEntity);
            id.add(imageEntity.getId());
        }
        return id;
    }

    @Override
    public byte[] download(long id) {
        try {
            ImageEntity imageEntity = this.imageRepository.getById(id);
            return imageEntity.getImage();
        }catch (EntityNotFoundException e){
            return null;
        }
    }



}
