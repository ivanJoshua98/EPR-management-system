package com.utopia_ok.epr_system.product.dto;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

public record ProductResponse(
  UUID id,
  String name,
  List<String> componentsNames,
  BigInteger priceCents,
  List<String> categoriesNames,
  String sku) { 
}
