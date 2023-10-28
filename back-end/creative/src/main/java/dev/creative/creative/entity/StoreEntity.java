package dev.creative.creative.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 상점 정보 테이블 스키마
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "store")
public class StoreEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String content;

    @Column
    private String email;
    @Column String phone;

    @Column
    private String address;

    @Column
    private Double coordinateX;

    @Column
    private Double coordinateY;

    @Column
    @ElementCollection
    @Builder.Default
    private List<Long> images = new ArrayList<>();

}
