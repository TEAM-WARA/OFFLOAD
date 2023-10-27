package dev.creative.creative.service.impl;

import dev.creative.creative.dao.StoreDAO;
import dev.creative.creative.dto.RangeDTO;
import dev.creative.creative.dto.StoreDTO;
import dev.creative.creative.entity.StoreEntity;
import dev.creative.creative.service.AddressService;
import dev.creative.creative.service.AuthService;
import dev.creative.creative.service.ImageService;
import dev.creative.creative.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


@Service
public class StoreServiceImpl implements StoreService {

    private final static Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);
    private final StoreDAO storeDAO;
    private final ImageService imageService;
    private final AddressService addressService;
    private final AuthService authService;


    public StoreServiceImpl(
            @Autowired ImageService imageService,
            @Autowired StoreDAO storeDAO,
            @Autowired AddressService addressService,
            @Autowired AuthService authService
    ){
        this.addressService = addressService;
        this.imageService = imageService;
        this.storeDAO = storeDAO;
        this.authService = authService;
    }

    @Override
    public ResponseEntity<StoreDTO> createStore(StoreDTO storeDTO, List<MultipartFile> images) throws IOException {

        List<Long> imageList = this.imageService.upload(images);
        LinkedHashMap coordinate = this.addressService.toCoordinate(storeDTO.getAddress());

        StoreEntity storeEntity = StoreEntity.builder()
                .name(storeDTO.getName())
                .email(storeDTO.getEmail())
                .address(storeDTO.getAddress())
                .coordinateX(coordinate.get("result").toString().equals("success")
                                ?  Double.valueOf(coordinate.get("x").toString()): 0)
                .coordinateY(coordinate.get("result").toString().equals("success")
                                ?  Double.valueOf(coordinate.get("y").toString()): 0)
                .images(imageList)
                .build();
        storeEntity = this.storeDAO.createStore(storeEntity);

        storeDTO = StoreDTO.builder()
                .id(storeEntity.getId())
                .name(storeEntity.getName())
                .email(storeEntity.getEmail())
                .address(storeEntity.getAddress())
                .coordinateX(storeEntity.getCoordinateX())
                .coordinateY(storeEntity.getCoordinateY())
                .images(storeEntity.getImages())
                .build();

        return ResponseEntity.status(201).body(storeDTO);
    }

    @Override
    public ResponseEntity<StoreDTO> readStore(Long id) {
        StoreEntity storeEntity = this.storeDAO.readStore(id);
        try{
            StoreDTO storeDTO = StoreDTO.builder()
                    .id(storeEntity.getId())
                    .name(storeEntity.getName())
                    .email(storeEntity.getEmail())
                    .address(storeEntity.getAddress())
                    .coordinateX(storeEntity.getCoordinateX())
                    .coordinateY(storeEntity.getCoordinateY())
                    .images(storeEntity.getImages())
                    .build();
            return ResponseEntity.status(200).body(storeDTO);
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    public ResponseEntity<List<StoreDTO>> readAllStore() {
        List<StoreDTO> stores= new ArrayList<>();
        for(StoreEntity storeEntity : this.storeDAO.readAllStore()){
            StoreDTO storeDTO = StoreDTO.builder()
                    .id(storeEntity.getId())
                    .name(storeEntity.getName())
                    .email(storeEntity.getEmail())
                    .address(storeEntity.getAddress())
                    .coordinateX(storeEntity.getCoordinateX())
                    .coordinateY(storeEntity.getCoordinateY())
                    .images(storeEntity.getImages())
                    .build();
            stores.add(storeDTO);
        }
        return ResponseEntity.status(200).body(stores);
    }

    @Override
    public ResponseEntity<List<StoreDTO>> readAllStoreBetweenSquare(RangeDTO rangeDTO) {
        List<StoreDTO> stores = new ArrayList<>();
        for(StoreEntity storeEntity : this.storeDAO.readAllStoreBetweenSquare(rangeDTO)){
            StoreDTO storeDTO = StoreDTO.builder()
                    .id(storeEntity.getId())
                    .name(storeEntity.getName())
                    .email(storeEntity.getEmail())
                    .address(storeEntity.getAddress())
                    .coordinateX(storeEntity.getCoordinateX())
                    .coordinateY(storeEntity.getCoordinateY())
                    .images(storeEntity.getImages())
                    .build();
            stores.add(storeDTO);
        }

        return ResponseEntity.status(200).body(stores);
    }

    @Override
    public ResponseEntity<StoreDTO> updateStore(Long id, StoreDTO storeDTO) {
        try{
            StoreEntity storeEntity = this.storeDAO.readStore(id);
            storeEntity.setName(storeEntity.getName().equals(storeDTO.getName()) || storeDTO.getName() == null
                    ? storeEntity.getName() : storeDTO.getName());
            storeEntity.setAddress(storeEntity.getAddress().equals(storeDTO.getAddress()) || storeDTO.getAddress() == null
                    ? storeEntity.getAddress() : storeDTO.getAddress());
            storeEntity = this.storeDAO.createStore(storeEntity);

            storeDTO = StoreDTO.builder()
                    .id(storeEntity.getId())
                    .name(storeEntity.getName())
                    .email(storeEntity.getEmail())
                    .address(storeEntity.getAddress())
                    .coordinateX(storeEntity.getCoordinateX())
                    .coordinateY(storeEntity.getCoordinateY())
                    .images(storeEntity.getImages())
                    .build();
            return ResponseEntity.status(200).body(storeDTO);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(400).body(null);
        }


    }

    @Override
    public ResponseEntity<Boolean> deleteStore(Long id) {
        if( this.storeDAO.deleteStore(id)){
            return ResponseEntity.status(200).body(true);
        }
        return ResponseEntity.status(400).body(false);
    }
}
