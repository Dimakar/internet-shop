package ru.dimakar.internetshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastname;
    private UserRoles role;
}
