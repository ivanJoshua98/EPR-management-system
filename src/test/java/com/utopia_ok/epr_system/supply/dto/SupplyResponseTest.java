package com.utopia_ok.epr_system.supply.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class SupplyResponseTest {

  @Test
  void aSupplyResponseIsEqualToAnotherSupplyResponseWhenAllFieldsAreEqual() {
    UUID id = UUID.randomUUID();
    SupplyResponse response1 = new SupplyResponse(id, "name", new BigInteger("123"), "providerName", 123);
    SupplyResponse response2 = new SupplyResponse(id, "name", new BigInteger("123"), "providerName", 123);
    assertEquals(response1, response2);
  }

  @Test
  void aSupplyResponseCanBeConvertedToString() {
    SupplyResponse response = new SupplyResponse(UUID.randomUUID(), "name", new BigInteger("123"), "providerName", 123);
    assertEquals("SupplyResponse[id=" + response.id() + ", name=" + response.name() + ", priceCents=" + response.priceCents() + ", providerName=" + response.providerName() + ", stock=" + response.stock() + "]", response.toString());
  }

  @Test
  void aSupplyResponseHasTheId() {
    SupplyResponse response = new SupplyResponse(UUID.randomUUID(), "name", new BigInteger("123"), "providerName", 123);
    assertEquals(response.id(), response.id());
  }

  @Test
  void aSupplyResponseHasTheName() {
    SupplyResponse response = new SupplyResponse(UUID.randomUUID(), "name", new BigInteger("123"), "providerName", 123);
    assertEquals("name", response.name());
  }

  @Test
  void aSupplyResponseHasThePriceCents() {
    SupplyResponse response = new SupplyResponse(UUID.randomUUID(), "name", new BigInteger("123"), "providerName", 123);
    assertEquals(new BigInteger("123"), response.priceCents());
  }

  @Test
  void aSupplyResponseHasTheProviderName() {
    SupplyResponse response = new SupplyResponse(UUID.randomUUID(), "name", new BigInteger("123"), "providerName", 123);
    assertEquals("providerName", response.providerName());
  }

  @Test
  void aSupplyResponseHasTheStock() {
    SupplyResponse response = new SupplyResponse(UUID.randomUUID(), "name", new BigInteger("123"), "providerName", 123);
    assertEquals(123, response.stock());
  }
}
