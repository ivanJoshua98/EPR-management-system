package com.utopia_ok.epr_system.supply.dto;

import java.math.BigInteger;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateSupplyRequest(
    @NotBlank(message = "Name is required, cannot be blank") String name,
    @NotNull(message = "Price is required") @Positive(message = "Price must be positive") BigInteger priceCents,
    String providerName,
    @Positive(message = "Stock must be positive") Integer stock) {
}
