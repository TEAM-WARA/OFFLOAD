package dev.creative.creative.service;

import dev.creative.creative.dto.StorageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StorageService {
    public ResponseEntity<StorageDTO> createStorage(StorageDTO storageDTO, List<MultipartFile> images) throws IOException;
    public ResponseEntity<StorageDTO> readStorage(Long id);
    public ResponseEntity<List<StorageDTO>> readAllStorage();
    public ResponseEntity<List<StorageDTO>> readAllStorageByStoreEmail(String email);
    public ResponseEntity<List<StorageDTO>> readAllStorageByEmail(String email);
    public ResponseEntity<Boolean> deleteStorage(Long id);


}
