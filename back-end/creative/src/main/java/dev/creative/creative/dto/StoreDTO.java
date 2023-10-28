package dev.creative.creative.dto;


import com.sun.istack.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

// Store Data Transfer Object
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class StoreDTO {
    private long id;
    private String name;
    private String content;
    private String address;
    private Double coordinateX;
    private Double coordinateY;
    private String email;
    @Builder.Default
    private List<Long> images = new ArrayList<>();
}
