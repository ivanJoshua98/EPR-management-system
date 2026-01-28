package com.utopia_ok.epr_system.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class SupplyProviderTest {

  private UUID anyId = UUID.randomUUID();
  private String anyName = "anyName";
  private Integer anyPhone = 123456789;
  private String anyPage = "https://anyPage.com";

  @Test
  void whenASupplyProviderIsCreatedWithId_thenIdIsNotNull_test() {
    SupplyProvider supplyProvider = SupplyProvider.builder()
                                                  .id(anyId)
                                                  .build();
    assertEquals(anyId, supplyProvider.getId());
  }

  @Test
  void whenASupplyProviderIsCreatedWithName_thenNameIsNotNull_test() {
    SupplyProvider supplyProvider = SupplyProvider.builder()
                                                  .name(anyName)
                                                  .build();
    assertEquals(anyName, supplyProvider.getName());
  }

  @Test
  void whenASupplyProviderIsCreatedWithPhone_thenPhoneIsNotNull_test() {
    SupplyProvider supplyProvider = SupplyProvider.builder()
                                                  .phone(anyPhone)
                                                  .build();
    assertEquals(anyPhone, supplyProvider.getPhone());
  }

  @Test
  void whenASupplyProviderIsCreatedWithPage_thenPageIsNotNull_test() {
    SupplyProvider supplyProvider = SupplyProvider.builder()
                                                  .page(anyPage)
                                                  .build();
    assertEquals(anyPage, supplyProvider.getPage());
  }
}
