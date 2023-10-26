package dev.creative.creative.service;


import dev.creative.creative.dto.StoreDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StoreService {
    public ResponseEntity<StoreDTO> createStore(StoreDTO storeDTO, List<MultipartFile> images) throws IOException;
    public ResponseEntity<StoreDTO> readStore(Long id);
    public ResponseEntity<List<StoreDTO>> readAllStore();
    public ResponseEntity<StoreDTO> updateStore(Long id, StoreDTO storeDTO);
    public ResponseEntity<Boolean> deleteStore(Long id);


}
