package com.utopia_ok.epr_system.contact.supplyProvider.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class SupplyProviderResponseTest {

  @Test
  void aSupplyProviderResponseIsEqualToAnotherSupplyProviderResponseWhenAllFieldsAreEqual() {
    UUID id = UUID.randomUUID();
    SupplyProviderResponse response1 = new SupplyProviderResponse(id, "name", 123, "page");
    SupplyProviderResponse response2 = new SupplyProviderResponse(id, "name", 123, "page");
    assertEquals(response1, response2);
  }

  @Test
  void aSupplyProviderResponseIsNotEqualToAnotherSupplyProviderResponseWhenAnyFieldIsDifferent() {
    SupplyProviderResponse response1 = new SupplyProviderResponse(UUID.randomUUID(), "name", 123, "page");
    SupplyProviderResponse response2 = new SupplyProviderResponse(UUID.randomUUID(), "name", 123, "page");
    assertNotEquals(response1, response2);
  }

  @Test
  void aSupplyProviderResponseHasTheId() {
    UUID id = UUID.randomUUID();
    SupplyProviderResponse response = new SupplyProviderResponse(id, "name", 123, "page");
    assertEquals(id, response.id());
  }

  @Test
  void aSupplyProviderResponseHasTheName() {
    UUID id = UUID.randomUUID();
    SupplyProviderResponse response = new SupplyProviderResponse(id, "name", 123, "page");
    assertEquals("name", response.name());
  }

  @Test
  void aSupplyProviderResponseHasThePhone() {
    UUID id = UUID.randomUUID();
    SupplyProviderResponse response = new SupplyProviderResponse(id, "name", 123, "page");
    assertEquals(123, response.phone());
  }

  @Test
  void aSupplyProviderResponseHasThePage() {
    UUID id = UUID.randomUUID();
    SupplyProviderResponse response = new SupplyProviderResponse(id, "name", 123, "page");
    assertEquals("page", response.page());
  }

  @Test
  void aSupplyProviderResponseCanBeConvertedToString() {
    UUID id = UUID.randomUUID();
    SupplyProviderResponse response = new SupplyProviderResponse(id, "name", 123, "page");
    assertEquals("SupplyProviderResponse[id=" + id + ", name=name, phone=123, page=page]", response.toString());
  }
}
