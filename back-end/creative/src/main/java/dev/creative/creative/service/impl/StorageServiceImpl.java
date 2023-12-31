package dev.creative.creative.service.impl;

import dev.creative.creative.dao.StorageDAO;
import dev.creative.creative.dto.StorageDTO;
import dev.creative.creative.entity.StorageEntity;
import dev.creative.creative.service.ImageService;
import dev.creative.creative.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


// 물품 보관 신청서 관련 구현체
@Service
public class StorageServiceImpl implements StorageService {

    private final static Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);
    private final StorageDAO storageDAO;
    private final ImageService imageService;
    @Autowired
    private EntityManager entityManager;


    public StorageServiceImpl(
            @Autowired ImageService imageService,
            @Autowired StorageDAO storageDAO
    ){
        this.imageService = imageService;
        this.storageDAO = storageDAO;
    }



    @Transactional
    @Override
    public ResponseEntity<StorageDTO> createStorage(StorageDTO storageDTO, List<MultipartFile> images) throws IOException {
        List<Long> imageList = this.imageService.upload(images);

        StorageEntity storageEntity = StorageEntity.builder()
                .name(storageDTO.getName())
                .storeEmail(storageDTO.getStoreEmail())
                .storeId(storageDTO.getStoreId())
                .allow(false)
                .phone(storageDTO.getPhone())
                .content(storageDTO.getContent())
                .images(imageList)
                .email(storageDTO.getEmail())
                .start(LocalDateTime.parse(storageDTO.getStart()))
                .expiration(LocalDateTime.parse(storageDTO.getExpiration()))
                .build();

        storageEntity = this.storageDAO.createStorage(storageEntity);
        storageDTO = StorageDTO.builder()
                .id(storageEntity.getId())
                .name(storageEntity.getName())
                .storeEmail(storageEntity.getStoreEmail())
                .storeId(storageEntity.getStoreId())
                .allow(false)
                .phone(storageEntity.getPhone())
                .content(storageEntity.getContent())
                .email(storageEntity.getEmail())
                .images(storageEntity.getImages())
                .start(storageEntity.getStart().toString())
                .expiration(storageEntity.getExpiration().toString())
                .build();

        return ResponseEntity.status(201).body(storageDTO);
    }

    @Override
    public ResponseEntity<StorageDTO> readStorage(Long id) {
        StorageEntity storageEntity = this.storageDAO.readStorage(id);
        if(storageEntity != null){
            StorageDTO storageDTO = StorageDTO.builder()
                    .id(storageEntity.getId())
                    .name(storageEntity.getName())
                    .storeEmail(storageEntity.getStoreEmail())
                    .storeId(storageEntity.getStoreId())
                    .allow(storageEntity.getAllow())
                    .phone(storageEntity.getPhone())
                    .email(storageEntity.getEmail())
                    .content(storageEntity.getContent())
                    .images(storageEntity.getImages())
                    .start(storageEntity.getStart().toString())
                    .expiration(storageEntity.getExpiration().toString())
                    .build();
            return ResponseEntity.status(200).body(storageDTO);
        }else{
            return ResponseEntity.status(400).body(null);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<List<StorageDTO>> readAllStorage() {
        List<StorageDTO> storages = new ArrayList<>();
        for(StorageEntity storageEntity : this.storageDAO.readAllStorage()){
            StorageDTO storageDTO = StorageDTO.builder()
                    .id(storageEntity.getId())
                    .name(storageEntity.getName())
                    .storeEmail(storageEntity.getStoreEmail())
                    .storeId(storageEntity.getStoreId())
                    .allow(storageEntity.getAllow())
                    .phone(storageEntity.getPhone())
                    .email(storageEntity.getEmail())
                    .content(storageEntity.getContent())
                    .images(storageEntity.getImages())
                    .start(storageEntity.getStart().toString())
                    .expiration(storageEntity.getExpiration().toString())
                    .build();
            storages.add(storageDTO);
        }

        return ResponseEntity.status(200).body(storages);
    }

    @Transactional
    @Override
    public ResponseEntity<List<StorageDTO>> readAllStorageByStoreEmail(String email) {
        List<StorageDTO> storages = new ArrayList<>();
        for(StorageEntity storageEntity : this.storageDAO.readAllStorageByStoreEmail(email)){
            StorageDTO storageDTO = StorageDTO.builder()
                    .id(storageEntity.getId())
                    .name(storageEntity.getName())
                    .storeEmail(storageEntity.getStoreEmail())
                    .storeId(storageEntity.getStoreId())
                    .allow(storageEntity.getAllow())
                    .phone(storageEntity.getPhone())
                    .email(storageEntity.getEmail())
                    .content(storageEntity.getContent())
                    .images(storageEntity.getImages())
                    .start(storageEntity.getStart().toString())
                    .expiration(storageEntity.getExpiration().toString())
                    .build();
            storages.add(storageDTO);
        }

        return ResponseEntity.status(200).body(storages);
    }

    @Transactional
    @Override
    public ResponseEntity<List<StorageDTO>> readAllStorageByEmail(String email) {
        List<StorageDTO> storages = new ArrayList<>();
        for(StorageEntity storageEntity : this.storageDAO.readAllStorageByEmail(email)){
            StorageDTO storageDTO = StorageDTO.builder()
                    .id(storageEntity.getId())
                    .name(storageEntity.getName())
                    .storeEmail(storageEntity.getStoreEmail())
                    .storeId(storageEntity.getStoreId())
                    .allow(storageEntity.getAllow())
                    .phone(storageEntity.getPhone())
                    .email(storageEntity.getEmail())
                    .content(storageEntity.getContent())
                    .images(storageEntity.getImages())
                    .start(storageEntity.getStart().toString())
                    .expiration(storageEntity.getExpiration().toString())
                    .build();
            storages.add(storageDTO);
        }

        return ResponseEntity.status(200).body(storages);
    }

    @Override
    public ResponseEntity<Boolean> deleteStorage(Long id) {
        if(this.storageDAO.deleteStorage(id)){
            return ResponseEntity.status(200).body(true);
        }else{
            return ResponseEntity.status(400).body(false);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<StorageDTO> allowStorage(String email, Long id) {
        try{
            StorageEntity storageEntity = this.storageDAO.readStorage(id);

            logger.info(storageEntity.toString());
            if (storageEntity.getStoreEmail().equals(email) && !storageEntity.getAllow()) {
                storageEntity.setAllow(true);
                storageEntity = this.storageDAO.createStorage(storageEntity);
                StorageDTO storageDTO = StorageDTO.builder()
                        .id(storageEntity.getId())
                        .name(storageEntity.getName())
                        .storeEmail(storageEntity.getStoreEmail())
                        .storeId(storageEntity.getStoreId())
                        .allow(storageEntity.getAllow())
                        .phone(storageEntity.getPhone())
                        .email(storageEntity.getEmail())
                        .content(storageEntity.getContent())
                        .images(storageEntity.getImages())
                        .start(storageEntity.getStart().toString())
                        .expiration(storageEntity.getExpiration().toString())
                        .build();
                return ResponseEntity.status(200).body(storageDTO);
            } else if (storageEntity.getAllow()) {
                return ResponseEntity.status(400).body(StorageDTO.builder().name("이미 허용되어 있습니다.").build());
            }
            return ResponseEntity.status(400).body(StorageDTO.builder().name("권한이 없는 요청서에 대한 접근입니다.").build());
        }catch (NullPointerException e) {
            return ResponseEntity.status(400).body(StorageDTO.builder().name("존재하지 않는 보관 요청입니다.").build());
        }
    }

    @Transactional
    @Override
    public ResponseEntity<StorageDTO> deniedStorage(String email, Long id) {
        try{
            StorageEntity storageEntity = this.storageDAO.readStorage(id);
            if (storageEntity.getStoreEmail().equals(email)) {
                storageEntity.setName("DENIED REQUEST");
                storageEntity.setAllow(false);
                storageEntity.setContent("물품 보관 요청이 거부되었습니다.");
                storageEntity = storageDAO.createStorage(storageEntity);
                StorageDTO storageDTO = StorageDTO.builder()
                        .id(storageEntity.getId())
                        .name(storageEntity.getName())
                        .storeEmail(storageEntity.getStoreEmail())
                        .storeId(storageEntity.getStoreId())
                        .allow(storageEntity.getAllow())
                        .phone(storageEntity.getPhone())
                        .email(storageEntity.getEmail())
                        .content(storageEntity.getContent())
                        .images(storageEntity.getImages())
                        .start(storageEntity.getStart().toString())
                        .expiration(storageEntity.getExpiration().toString())
                        .build();
                return ResponseEntity.status(200).body(storageDTO);
            }

            return ResponseEntity.status(403).body(StorageDTO.builder().name("권한이 없는 요청서에 대한 접근입니다.").build());
        }catch (NullPointerException e){
            return ResponseEntity.status(400).body(StorageDTO.builder().name("존재하지 않는 요청입니다.").build());
        }
    }


    @Transactional
    @Override
    public ResponseEntity<List<StorageDTO>> readAllStorageByAllowed(String email) {
        List<StorageEntity> entities = this.storageDAO.readAllStorageByEmailAndAllow(email);
        if(entities != null){
            List<StorageDTO> storages = new ArrayList<>();
            for(StorageEntity storageEntity : entities){
                StorageDTO storageDTO = StorageDTO.builder()
                        .id(storageEntity.getId())
                        .name(storageEntity.getName())
                        .storeEmail(storageEntity.getStoreEmail())
                        .storeId(storageEntity.getStoreId())
                        .allow(storageEntity.getAllow())
                        .phone(storageEntity.getPhone())
                        .email(storageEntity.getEmail())
                        .content(storageEntity.getContent())
                        .images(storageEntity.getImages())
                        .start(storageEntity.getStart().toString())
                        .expiration(storageEntity.getExpiration().toString())
                        .build();
                storages.add(storageDTO);
            }
            return ResponseEntity.status(200).body(storages);
        }
        return ResponseEntity.status(400).body(null);
    }
}
