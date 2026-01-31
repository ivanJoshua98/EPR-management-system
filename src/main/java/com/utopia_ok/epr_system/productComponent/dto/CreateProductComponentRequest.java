package com.utopia_ok.epr_system.productComponent.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateProductComponentRequest(
  @NotNull(message = "Supply ID is required") UUID supplyId,
  @NotNull(message = "Quantity is required") @Positive(message = "Quantity must be positive") Integer quantity
) {}
