package com.utopia_ok.epr_system.supply;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.utopia_ok.epr_system.contact.SupplyProvider;
import com.utopia_ok.epr_system.price.SupplyPrice;

public class SupplyTest {

  private UUID anyId;
  private SupplyProvider anyProvider;
  private SupplyPrice anyPrice;
  private Supply anySupply;

  @BeforeEach
  void setUp() {
    anyProvider = Mockito.mock(SupplyProvider.class);
    anyPrice = Mockito.mock(SupplyPrice.class);
    anyId = UUID.randomUUID();
    anySupply = new Supply();
  }

  @Test
  void whenASupplyIsCreatedWithId_thenIdIsNotNull_test() {
    Supply supply = Supply.builder()
                          .id(anyId)
                          .build();
    assertEquals(anyId, supply.getId());
  }

  @Test
  void whenASupplyIsCreatedWithName_thenNameIsNotNull_test() {
    Supply supply = Supply.builder()
                          .name("Test name")
                          .build();
    assertEquals("Test name", supply.getName());
  }

  @Test
  void whenASupplyIsCreatedWithPrices_thenPricesIsNotNull_test() {
    Supply supply = Supply.builder()
                          .prices(List.of(anyPrice))
                          .build();
    assertEquals(List.of(anyPrice), supply.getPrices());
  }

  @Test
  void whenASupplyIsCreatedWithProvider_thenProviderIsNotNull_test() {
    Supply supply = Supply.builder()
                          .provider(anyProvider)
                          .build();
    assertEquals(anyProvider, supply.getProvider());
  }

  @Test
  void whenASupplyIsCreatedWithStock_thenStockIsNotNull_test() {
    Supply supply = Supply.builder()
                          .stock(1)
                          .build();
    assertEquals(1, supply.getStock());
  }

  @Test
  void whenTheNewNameIsSetInASupply_thenTheNameChanges_test() {
    anySupply.setName("New name");
    assertEquals("New name", anySupply.getName());
  }

  @Test
  void whenThePricesAreSetInASupply_thenThePricesChange_test() {
    anySupply.setPrices(List.of(anyPrice));
    assertEquals(List.of(anyPrice), anySupply.getPrices());
  }

  @Test
  void whenTheProviderIsSetInASupply_thenTheProviderChanges_test() {
    anySupply.setProvider(anyProvider);
    assertEquals(anyProvider, anySupply.getProvider());
  }

  @Test
  void whenTheStockIsSetInASupply_thenTheStockChanges_test() {
    anySupply.setStock(1);
    assertEquals(1, anySupply.getStock());
  }

  @Test
  void whenACertainAmountIsAdded_thenTheStockIncrements_test() {
    anySupply.setStock(1);
    Integer oldStock = anySupply.getStock();

    anySupply.addStock(2);
    assertEquals(oldStock + 2, anySupply.getStock());
  }

  @Test
  void whenACertainAmountIsRemoved_thenTheStockDecrements_test() {
    anySupply.setStock(4);
    Integer oldStock = anySupply.getStock();

    anySupply.removeStock(1);
    assertEquals(oldStock - 1, anySupply.getStock());
  }

  @Test
  void whenTheCurrentPriceIsUpdated_thenThePriceChanges_test() {
    List<SupplyPrice> prices = new ArrayList<>();
    anySupply.setPrices(prices);

    anySupply.updateCurrentPrice(new Date(), 315.0);
    assertEquals(315.0, anySupply.getCurrentPrice().getValue());
  }
}
