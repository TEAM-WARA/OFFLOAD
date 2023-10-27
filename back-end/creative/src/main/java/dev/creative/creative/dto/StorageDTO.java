package dev.creative.creative.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class StorageDTO {
    private Long id;
    private String name;
    private String storeEmail;
    private String content;
    private String start;
    private String expiration;
    @Builder.Default
    private List<Long> images = new ArrayList<>();
    private String email;
    private Boolean allow;
    private String phone;

}
