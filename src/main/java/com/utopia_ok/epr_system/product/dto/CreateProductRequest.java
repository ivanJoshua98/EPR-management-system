package com.utopia_ok.epr_system.product.dto;

import java.math.BigInteger;
import java.util.List;

import com.utopia_ok.epr_system.productComponent.dto.CreateProductComponentRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateProductRequest(
  @NotBlank(message = "Name is required") String name, 
  List<CreateProductComponentRequest> components, 
  @NotNull(message = "Price is required") @Positive(message = "Price must be positive") BigInteger priceCents,
  List<String> categoriesNames, 
  @NotBlank(message = "SKU is required") String sku) {  
}
