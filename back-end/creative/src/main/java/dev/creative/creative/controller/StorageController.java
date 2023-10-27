package dev.creative.creative.controller;


import dev.creative.creative.dto.StorageDTO;
import dev.creative.creative.entity.StorageEntity;
import dev.creative.creative.service.StorageService;
import io.swagger.annotations.*;
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

    @ApiOperation(value = "Create Storage", notes = "물품 보관 요청")
    @ApiResponses({
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=400, message = "값이 없습니다."),
            @ApiResponse(code=401, message = "인증된 사용자가 아닙니다."),
            @ApiResponse(code=403, message = "접근 권한이 없습니다.")
    })
    @PostMapping
    public ResponseEntity<StorageDTO> createStorage(
            @RequestPart("data") StorageDTO storageDTO,
            @RequestPart("images")List<MultipartFile> images
            ) throws IOException {
        return this.storageService.createStorage(storageDTO, images);
    }

    @ApiOperation(value = "Read Storage", notes = "물품 보관 요청 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "물품 보관 요청 인덱스 번호", required = true, dataType = "long", example = "1", defaultValue = "")
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=400, message = "값이 없습니다."),
            @ApiResponse(code=401, message = "인증된 사용자가 아닙니다."),
            @ApiResponse(code=403, message = "접근 권한이 없습니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StorageDTO> readStorage(
            @PathVariable("id") Long id
    ){
        return this.storageService.readStorage(id);
    }

    @ApiOperation(value = "Read ALL Storage", notes = "물품 보관 요청 모두 조회")
    @ApiResponses({
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=400, message = "값이 없습니다."),
            @ApiResponse(code=401, message = "인증된 사용자가 아닙니다."),
            @ApiResponse(code=403, message = "접근 권한이 없습니다.")
    })
    @GetMapping()
    public ResponseEntity<List<StorageDTO>> readAllStorage(){
        return this.storageService.readAllStorage();
    }

    @ApiOperation(value = "Read ALL Storage By STORE", notes = "상점별 물품 보관 요청 모두 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "상점 주인 이메일", required = true, dataType = "String", defaultValue = "")
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=400, message = "값이 없습니다."),
            @ApiResponse(code=401, message = "인증된 사용자가 아닙니다."),
            @ApiResponse(code=403, message = "접근 권한이 없습니다.")
    })
    @GetMapping("/seller")
    public ResponseEntity<List<StorageDTO>> readAllStorageByStoreEmail(
            @RequestParam("email") String email
    ){
        return this.storageService.readAllStorageByStoreEmail(email);
    }

    @ApiOperation(value = "Read ALL Storage By User", notes = "유저별 모든 물품 보관 요청 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "유저 이메일", required = true, dataType = "String", defaultValue = "")
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=400, message = "값이 없습니다."),
            @ApiResponse(code=401, message = "인증된 사용자가 아닙니다."),
            @ApiResponse(code=403, message = "접근 권한이 없습니다.")
    })
    @GetMapping("/user")
    public ResponseEntity<List<StorageDTO>> readAllStorageByEmail(
            @RequestParam("email") String email
    ){
        return this.storageService.readAllStorageByEmail(email);
    }

    @ApiOperation(value = "Delete Storage", notes = "물품 보관 요청 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "물품 보관 요청 인덱스 번호", required = true, dataType = "long", example = "1", defaultValue = "")
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=400, message = "값이 없습니다."),
            @ApiResponse(code=401, message = "인증된 사용자가 아닙니다."),
            @ApiResponse(code=403, message = "접근 권한이 없습니다.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteStorage(
            @PathVariable("id") Long id
    ){
        return this.storageService.deleteStorage(id);
    }


    @PutMapping("/allow/{id}")
    public ResponseEntity<StorageDTO> allowStorage(
            @PathVariable("id") Long id,
            @RequestParam("email") String email
    ){
        return this.storageService.allowStorage(email, id);
    }

    @PutMapping("/denied/{id}")
    public ResponseEntity<StorageDTO> deniedStorage(
            @PathVariable("id") Long id,
            @RequestParam("email") String email
    ){
        return this.storageService.deniedStorage(email, id);
    }

    @GetMapping("/seller/allow")
    public ResponseEntity<List<StorageDTO>> readAllStorageByStoreEmailAndAllowed(
        @RequestParam("email") String email
    ){
        return this.storageService.readAllStorageByAllowed(email);
    }


}
