package dev.creative.creative.dao.impl;

import dev.creative.creative.dao.StorageDAO;
import dev.creative.creative.entity.StorageEntity;
import dev.creative.creative.repository.StorageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class StorageDAOImpl implements StorageDAO {

    private static final Logger logger = LoggerFactory.getLogger(StorageDAOImpl.class);
    private final StorageRepository storageRepository;

    public StorageDAOImpl(
            @Autowired StorageRepository storageRepository
    ){
        this.storageRepository = storageRepository;
    }



    @Override
    public StorageEntity createStorage(StorageEntity storageEntity) {
        return this.storageRepository.save(storageEntity);
    }

    @Override
    public StorageEntity readStorage(Long id) {
        if(storageRepository.existsById(id)){
            return storageRepository.getById(id);
        }else{
            return null;
        }
    }

    @Override
    public List<StorageEntity> readAllStorage() {
        return this.storageRepository.findAll();
    }

    @Override
    public List<StorageEntity> readAllStorageByStoreEmail(String email) {
        return this.storageRepository.getAllByStoreEmail(email);
    }

    @Override
    public List<StorageEntity> readAllStorageByEmail(String email) {
        return this.storageRepository.getAllByEmail(email);
    }

    @Override
    public Boolean deleteStorage(Long id) {
        if(this.storageRepository.existsById(id)){
            this.storageRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }
}
