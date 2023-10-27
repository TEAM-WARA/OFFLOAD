package dev.creative.creative.dao.impl;

import dev.creative.creative.dao.StoreDAO;
import dev.creative.creative.dto.RangeDTO;
import dev.creative.creative.entity.StoreEntity;
import dev.creative.creative.repository.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Repository
public class StoreDAOImpl implements StoreDAO {

    private final static Logger logger = LoggerFactory.getLogger(StoreDAOImpl.class);
    private final StoreRepository storeRepository;

    public StoreDAOImpl(
            @Autowired StoreRepository storeRepository
    ){
        this.storeRepository = storeRepository;
    }

    @Override
    public StoreEntity createStore(StoreEntity storeEntity) {
        return this.storeRepository.save(storeEntity);
    }

    @Override
    public StoreEntity readStore(long id) {
        try {
            return storeRepository.getById(id);
        }catch (EntityNotFoundException e){
            return null;
        }
    }

    @Override
    public List<StoreEntity> readAllStore() {
        return this.storeRepository.findAll();
    }

    @Override
    public List<StoreEntity> readAllStoreBetweenSquare(RangeDTO rangeDTO) {
        return this.storeRepository
                .getStoreEntitiesByCoordinateXBetweenAndCoordinateYBetween(rangeDTO.getMinX(), rangeDTO.getMaxX(),rangeDTO.getMinY(),  rangeDTO.getMaxY());
    }

    @Override
    public boolean deleteStore(long id) {
        if(this.storeRepository.existsById(id)){
            this.storeRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
