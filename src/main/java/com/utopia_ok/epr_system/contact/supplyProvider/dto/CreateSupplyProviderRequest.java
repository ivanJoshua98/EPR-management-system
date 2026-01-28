package com.utopia_ok.epr_system.contact.supplyProvider.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateSupplyProviderRequest(
  @NotBlank(message = "Name is required, cannot be blank") String name,
  Integer phone,
  String page) {
}
