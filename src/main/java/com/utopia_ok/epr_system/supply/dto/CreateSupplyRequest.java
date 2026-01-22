package com.utopia_ok.epr_system.supply.dto;

import java.math.BigInteger;

public record CreateSupplyRequest(String name, BigInteger priceCents, String providerName, Integer stock) {
}
