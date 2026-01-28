package com.utopia_ok.epr_system.price.dto;

import java.math.BigInteger;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdatePriceRequest(
    @NotNull(message = "Price is required") @Positive(message = "Price must be positive") BigInteger priceCents,
    @NotBlank(message = "Date is required") String date) {
}
