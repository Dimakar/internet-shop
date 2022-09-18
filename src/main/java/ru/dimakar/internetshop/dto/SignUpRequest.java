package ru.dimakar.internetshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignUpRequest {
    @NotEmpty
    @Email
    @JsonProperty("email")
    private String email;
    @NotEmpty
    @JsonProperty("first_name")
    private String firstName;
    @NotEmpty
    @JsonProperty("last_name")
    private String lastName;
    @NotEmpty
    @Size(min = 12, message = "Password length must be 12 chars minimum!")
    @JsonProperty("password")
    private String password;

}