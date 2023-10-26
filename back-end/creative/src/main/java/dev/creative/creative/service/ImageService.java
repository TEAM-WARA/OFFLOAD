package dev.creative.creative.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    public List<Long> upload(List<MultipartFile> images) throws IOException;
    public byte[] download(long id);


}
