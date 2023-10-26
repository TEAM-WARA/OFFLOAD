package dev.creative.creative.controller;


import dev.creative.creative.dto.StoreDTO;
import dev.creative.creative.service.StoreService;
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
@RequestMapping("/store")
public class StoreController {
    private final static Logger logger = LoggerFactory.getLogger(StoreController.class);
    private final StoreService storeService;

    public StoreController(
            @Autowired StoreService storeService
    ){
        this.storeService = storeService;
    }


    @ApiOperation(value = "Create Store", notes = "상점 등록")
    @ApiResponses({
            @ApiResponse(code=201, message = "상점 등록 성공"),
            @ApiResponse(code=400, message = "잘못된 값입니다."),
            @ApiResponse(code=401, message =  "인증된 사용자가 아닙니다."),
            @ApiResponse(code=403, message = "접근 권한이 없습니다.")
    })
    @PostMapping
    public ResponseEntity<StoreDTO> createStore(
            @RequestPart("data") StoreDTO storeDTO,
            @RequestPart("images") List<MultipartFile> images
            ) throws IOException {
        return this.storeService.createStore(storeDTO, images);
    }

    @ApiOperation(value = "Read Store", notes = "상점 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "상점 인덱스 번호", required = true, dataType = "long", example = "1", defaultValue = "")
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=400, message = "값이 없습니다."),
            @ApiResponse(code=401, message =  "인증된 사용자가 아닙니다."),
            @ApiResponse(code=403, message = "접근 권한이 없습니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StoreDTO> readStore(
            @PathVariable("id") Long id
    ){
        return this.storeService.readStore(id);
    }

    @ApiOperation(value = "Read ALL Store", notes = "모든 상점 정보 조회")
    @ApiResponses({
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=400, message = "값이 없습니다."),
            @ApiResponse(code=401, message = "인증된 사용자가 아닙니다."),
            @ApiResponse(code=403, message = "접근 권한이 없습니다.")
    })
    @GetMapping()
    public ResponseEntity<List<StoreDTO>> readAllStore(){
        return this.storeService.readAllStore();
    }

    @ApiOperation(value = "Update Store", notes = "상점 정보 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "상점 인덱스 번호", required = true, dataType = "long", example = "1", defaultValue = "")
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=400, message = "값이 없습니다."),
            @ApiResponse(code=401, message = "인증된 사용자가 아닙니다."),
            @ApiResponse(code=403, message = "접근 권한이 없습니다.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<StoreDTO> updateStore(
            @PathVariable("id") Long id,
            @RequestBody StoreDTO storeDTO
    ){
        return this.storeService.updateStore(id, storeDTO);
    }

    @ApiOperation(value = "Delete Store", notes = "상점 정보 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "상점 인덱스 번호", required = true, dataType = "long", example = "1", defaultValue = "")
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=400, message = "값이 없습니다."),
            @ApiResponse(code=401, message = "인증된 사용자가 아닙니다."),
            @ApiResponse(code=403, message = "접근 권한이 없습니다.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteStore(
            @PathVariable("id") Long id
    ){
        return this.storeService.deleteStore(id);
    }
}
