package com.utopia_ok.epr_system.contact.supplyProvider.dto;

import java.util.UUID;

public record SupplyProviderResponse(UUID id, String name, Integer phone, String page) {
}
