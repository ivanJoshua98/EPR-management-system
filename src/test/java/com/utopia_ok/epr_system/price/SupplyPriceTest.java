package com.utopia_ok.epr_system.price;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigInteger;
import java.time.LocalDate;
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
                                        .priceCents(BigInteger.valueOf(2500))
                                        .build();
    assertEquals(BigInteger.valueOf(2500), supplyPrice.getPriceCents());
  }

  @Test
  void whenASupplyPriceIsCreatedWithFromDate_thenFromDateIsNotNull_test() {
    LocalDate now = LocalDate.now();
    SupplyPrice supplyPrice = SupplyPrice.builder()
                                        .fromDate(now)
                                        .build();
    assertEquals(now, supplyPrice.getFromDate());
  }

  @Test
  void whenASupplyPriceIsCreatedWithToDate_thenToDateIsNotNull_test() {
    LocalDate now = LocalDate.now();
    SupplyPrice supplyPrice = SupplyPrice.builder()
                                        .toDate(now)
                                        .build();
    assertEquals(now, supplyPrice.getToDate());
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
                                        .fromDate(LocalDate.now())
                                        .priceCents(BigInteger.valueOf(2500))
                                        .build();
    assertNull(supplyPrice.getToDate());
    assertEquals(true, supplyPrice.isCurrent());
  }

  @Test 
  void whenASupplyPriceIsClosed_thenToDateIsNotNull_test() {
    SupplyPrice supplyPrice = SupplyPrice.builder()
                                        .fromDate(LocalDate.now())
                                        .priceCents(BigInteger.valueOf(2500))
                                        .build();
    LocalDate closeDate = LocalDate.now();
    supplyPrice.close(closeDate);

    assertEquals(closeDate, supplyPrice.getToDate());
  }

  @Test
  void whenASupplyPriceIsClosed_thenIsNoLongerCurrent_test() {
    SupplyPrice supplyPrice = SupplyPrice.builder()
                                        .fromDate(LocalDate.now())
                                        .priceCents(BigInteger.valueOf(2500))
                                        .build();
    LocalDate closeDate = LocalDate.now();
    supplyPrice.close(closeDate);
    
    assertFalse(supplyPrice.isCurrent());
  } 

  @Test
  void whenASupplyPriceHasNullDateFromAndNullDateTo_thenIsNotCurrent_test() {
    SupplyPrice supplyPrice = SupplyPrice.builder()
                                        .fromDate(null)
                                        .toDate(null)
                                        .build();
    assertEquals(false, supplyPrice.isCurrent());
  }

  @Test
  void whenASupplyPriceHasNullDateFromAndNotNullDateTo_thenIsNotCurrent_test() {
    SupplyPrice supplyPrice = SupplyPrice.builder()
                                        .fromDate(null)
                                        .toDate(LocalDate.now())
                                        .build();
    assertEquals(false, supplyPrice.isCurrent());
  }

}
