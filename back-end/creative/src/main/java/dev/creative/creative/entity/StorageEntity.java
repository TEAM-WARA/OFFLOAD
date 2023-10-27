package dev.creative.creative.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
    private String content;
    @Column
    private LocalDateTime start;
    @Column
    private LocalDateTime expiration;
    @Column
    private String email;

    @Column
    @Builder.Default
    @ElementCollection
    private List<Long> images = new ArrayList<>();
    @Column
    private Boolean allow;

}
