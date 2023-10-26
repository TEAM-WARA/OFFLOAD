package dev.creative.creative.controller;


import dev.creative.creative.dto.StoreDTO;
import dev.creative.creative.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {
    private final static Logger logger = LoggerFactory.getLogger(StoreController.class);
    private final StoreService storeService;

    public StoreController(
            @Autowired StoreService storeService
    ){
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<StoreDTO> createStore(
            @RequestPart("data") StoreDTO storeDTO,
            @RequestPart("images") List<MultipartFile> images
            ) throws IOException {
        return this.storeService.createStore(storeDTO, images);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDTO> readStore(
            @PathVariable("id") Long id
    ){
        return this.storeService.readStore(id);
    }

    @GetMapping()
    public ResponseEntity<List<StoreDTO>> readAllStore(){
        return this.storeService.readAllStore();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreDTO> updateStore(
            @PathVariable("id") Long id,
            @RequestBody StoreDTO storeDTO
    ){
        return this.storeService.updateStore(id, storeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteStore(
            @PathVariable("id") Long id
    ){
        return this.storeService.deleteStore(id);
    }
}
