package dev.creative.creative.service;

import java.util.LinkedHashMap;

// 주소, 지리좌표관련 비즈니스 로직
public interface AddressService {
    // 주소를 지리좌표로 변환 openAPI활용
    public LinkedHashMap toCoordinate(String address);
    // 지리좌표를 주소로 변환 openAPI활용
    public String toAddress(Double x, Double y);
    // 두 지리좌표 간의 거리 계산
    public Double dis(Double x1, Double y1, Double x2, Double y2);


}
