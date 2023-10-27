package dev.creative.creative.dto;


import lombok.*;

// Range Data Transfer Object
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RangeDTO {
    private Double minX;
    private Double minY;
    private Double maxX;
    private Double maxY;

}
