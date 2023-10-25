package dev.creative.creative.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class UserDTO {
    private String email;
    private String password;

}
