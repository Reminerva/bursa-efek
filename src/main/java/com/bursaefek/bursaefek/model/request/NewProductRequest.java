package com.bursaefek.bursaefek.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewProductRequest {

    @NotBlank(message = "name is required")
    private String productName;
    @Min(value = 0, message = "price cannot be negative")
    private Integer price;
    @Min(value = 0, message = "stock cannot be negative")
    private Integer stock;
    @NotBlank(message = "categoryProductId is required")
    private String categoryProductId;

}
