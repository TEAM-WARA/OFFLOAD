package dev.creative.creative.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



// 물품 보관 요청서 테이블 스키마
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "storage")
public class StorageEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String storeEmail;
    @Column
    private Long storeId;
    @Column
    private String content;
    @Column
    private LocalDateTime start;
    @Column
    private LocalDateTime expiration;
    @Column
    private String email;

    @Column
    private String phone;

    @Column
    @Builder.Default
    @ElementCollection
    private List<Long> images = new ArrayList<>();
    @Column
    private Boolean allow;

}
