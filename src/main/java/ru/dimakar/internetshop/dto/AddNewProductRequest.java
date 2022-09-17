package ru.dimakar.internetshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddNewProductRequest {
    @NotEmpty(message = "Product name must be not empty")
    private String name;
    @NotNull
    @Min(value = 0L, message = "Price must be positive value")
    private Long price;
    @NotNull
    @Min(value = 0, message = "Amount must be positive value")
    private Integer amount;
}
