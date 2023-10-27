package dev.creative.creative.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

// 이미지 처리와 관련된 비즈니스 로직
public interface ImageService {
    // 이미지를 업로드하고 이미지 id값 반환
    public List<Long> upload(List<MultipartFile> images) throws IOException;
    // 이미지 조회
    public byte[] download(long id);


}
