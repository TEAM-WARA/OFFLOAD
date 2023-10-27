package dev.creative.creative.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class TokenDTO {
    private String token;
    private String email;
}
