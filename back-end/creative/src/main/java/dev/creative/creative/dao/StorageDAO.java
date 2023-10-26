package dev.creative.creative.dao;

import dev.creative.creative.entity.StorageEntity;

import java.util.List;

public interface StorageDAO {
    public StorageEntity createStorage(StorageEntity storageEntity);
    public StorageEntity readStorage(Long id);
    public List<StorageEntity> readAllStorage();
    public List<StorageEntity> readAllStorageByStoreEmail(String email);
    public List<StorageEntity> readAllStorageByEmail(String email);
    public Boolean deleteStorage(Long id);

}
