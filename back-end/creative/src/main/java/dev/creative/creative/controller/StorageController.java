package dev.creative.creative.controller;


import dev.creative.creative.dto.StorageDTO;
import dev.creative.creative.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/storage")
public class StorageController {
    private final static Logger logger = LoggerFactory.getLogger(StorageController.class);
    private final StorageService storageService;

    public StorageController(
            @Autowired StorageService storageService
    ){
        this.storageService = storageService;
    }

    @PostMapping
    public ResponseEntity<StorageDTO> createStorage(
            @RequestPart("data") StorageDTO storageDTO,
            @RequestPart("images")List<MultipartFile> images
            ) throws IOException {
        return this.storageService.createStorage(storageDTO, images);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StorageDTO> readStorage(
            @PathVariable("id") Long id
    ){
        return this.storageService.readStorage(id);
    }

    @GetMapping()
    public ResponseEntity<List<StorageDTO>> readAllStorage(){
        return this.storageService.readAllStorage();
    }

    @GetMapping("/seller")
    public ResponseEntity<List<StorageDTO>> readAllStorageByStoreEmail(
            @RequestParam("email") String email
    ){
        return this.storageService.readAllStorageByStoreEmail(email);
    }

    @GetMapping("/user")
    public ResponseEntity<List<StorageDTO>> readAllStorageByEmail(
            @RequestParam("email") String email
    ){
        return this.storageService.readAllStorageByEmail(email);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteStorage(
            @PathVariable("id") Long id
    ){
        return this.storageService.deleteStorage(id);
    }


}
