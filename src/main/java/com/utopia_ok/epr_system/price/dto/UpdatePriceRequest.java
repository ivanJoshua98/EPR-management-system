package com.utopia_ok.epr_system.price.dto;

import java.math.BigInteger;

public record UpdatePriceRequest(BigInteger priceCents, String date) {
  
}
