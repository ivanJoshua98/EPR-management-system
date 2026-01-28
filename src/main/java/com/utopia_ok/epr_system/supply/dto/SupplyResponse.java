package com.utopia_ok.epr_system.supply.dto;

import java.math.BigInteger;
import java.util.UUID;

public record SupplyResponse(UUID id, String name, BigInteger priceCents, String providerName, Integer stock) {
}
