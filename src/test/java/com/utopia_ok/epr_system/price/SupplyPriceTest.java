package com.utopia_ok.epr_system.price;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.utopia_ok.epr_system.supply.Supply;

public class SupplyPriceTest {

  private UUID anyId;
  private Supply anySupply;

  @BeforeEach
  void setUp() {
    anyId = UUID.randomUUID();
    anySupply = Mockito.mock(Supply.class);
  }

  @Test
  void whenASupplyPriceIsCreatedWithId_thenIdIsNotNull_test() {
    SupplyPrice supplyPrice = SupplyPrice.builder()
                                        .id(anyId)
                                        .build();
    assertEquals(anyId, supplyPrice.getId());
  }

  @Test
  void whenASupplyPriceIsCreatedWithValue_thenValueIsNotNull_test() {
    SupplyPrice supplyPrice = SupplyPrice.builder()
                                        .value(250.0)
                                        .build();
    assertEquals(250.0, supplyPrice.getValue());
  }

  @Test
  void whenASupplyPriceIsCreatedWithFromDate_thenFromDateIsNotNull_test() {
    SupplyPrice supplyPrice = SupplyPrice.builder()
                                        .fromDate(new Date())
                                        .build();
    assertEquals(new Date(), supplyPrice.getFromDate());
  }

  @Test
  void whenASupplyPriceIsCreatedWithToDate_thenToDateIsNotNull_test() {
    SupplyPrice supplyPrice = SupplyPrice.builder()
                                        .toDate(new Date())
                                        .build();
    assertEquals(new Date(), supplyPrice.getToDate());
  }

  @Test
  void whenASupplyPriceIsCreatedWithSupply_thenSupplyIsNotNull_test() {
    SupplyPrice supplyPrice = SupplyPrice.builder()
                                        .supply(anySupply)
                                        .build();
    assertEquals(anySupply, supplyPrice.getSupply());
  }

  @Test
  void ACurrentSupplyIsCurrentWhenToDateIsNullAndFromDateIsNotNull_test() {
    SupplyPrice supplyPrice = SupplyPrice.builder()
                                        .fromDate(new Date())
                                        .value(250.0)
                                        .build();
    assertEquals(true, supplyPrice.isCurrent());
    assertNull(supplyPrice.getToDate());
  }

  @Test 
  void whenASupplyPriceIsClosed_thenToDateIsNotNull_test() {
    SupplyPrice supplyPrice = SupplyPrice.builder()
                                        .fromDate(new Date())
                                        .value(250.0)
                                        .build();
    Date closeDate = new Date();
    supplyPrice.close(closeDate);

    assertEquals(closeDate, supplyPrice.getToDate());
  }

  @Test
  void whenASupplyPriceIsClosed_thenIsNoLongerCurrent_test() {
    SupplyPrice supplyPrice = SupplyPrice.builder()
                                        .fromDate(new Date())
                                        .value(250.0)
                                        .build();
    Date closeDate = new Date();
    supplyPrice.close(closeDate);
    
    assertFalse(supplyPrice.isCurrent());
  } 

}
