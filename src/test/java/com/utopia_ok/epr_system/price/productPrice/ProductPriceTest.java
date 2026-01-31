package com.utopia_ok.epr_system.price.productPrice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.utopia_ok.epr_system.product.Product;

public class ProductPriceTest {

  private Product anyProduct;

  @BeforeEach
  void setUp() {
    anyProduct = Mockito.mock(Product.class);
  }

  @Test
  void whenAProductPriceIsCreatedWithId_thenIdIsNotNull_test() {
    UUID anyId = UUID.randomUUID();
    ProductPrice productPrice = ProductPrice.builder()
                                            .id(anyId)
                                            .build();
    assertEquals(anyId, productPrice.getId());
  }

  void whenAProductPriceIsCreatedWithPrice_thenPriceIsNotNull_test() {
    ProductPrice productPrice = ProductPrice.builder()
                                            .priceCents(BigInteger.valueOf(2500))
                                            .build();
    assertEquals(BigInteger.valueOf(2500), productPrice.getPriceCents());
  }

  void whenAProductPriceIsCreatedWithFromDate_thenFromDateIsNotNull_test() {
    ProductPrice productPrice = ProductPrice.builder()
                                            .fromDate(LocalDate.now())
                                            .build();
    assertEquals(LocalDate.now(), productPrice.getFromDate());
  }

  void whenAProductPriceIsCreatedWithToDate_thenToDateIsNotNull_test() {
    ProductPrice productPrice = ProductPrice.builder()
                                            .toDate(LocalDate.now())
                                            .build();
    assertEquals(LocalDate.now(), productPrice.getToDate());
  }

  void whenAProductPriceIsCreatedWithProduct_thenProductIsNotNull_test() {
    ProductPrice productPrice = ProductPrice.builder()
                                            .product(anyProduct)
                                            .build();
    assertEquals(anyProduct, productPrice.getProduct());
  }

  void whenAProductPriceIsClosed_thenToDateIsNotNull_test() {
    ProductPrice productPrice = ProductPrice.builder()
                                            .fromDate(LocalDate.now())
                                            .priceCents(BigInteger.valueOf(2500))
                                            .build();
    LocalDate closeDate = LocalDate.now();
    productPrice.close(closeDate);

    assertEquals(closeDate, productPrice.getToDate());
  }

  void whenAProductPriceIsClosed_thenIsNoLongerCurrent_test() {
    ProductPrice productPrice = ProductPrice.builder()
                                            .fromDate(LocalDate.now())
                                            .priceCents(BigInteger.valueOf(2500))
                                            .build();
    LocalDate closeDate = LocalDate.now();
    productPrice.close(closeDate);
    
    assertFalse(productPrice.isCurrent());
  } 

  void whenAProductPriceHasNullDateFromAndNullDateTo_thenIsNotCurrent_test() {
    ProductPrice productPrice = ProductPrice.builder()
                                            .fromDate(null)
                                            .toDate(null)
                                            .build();
    assertEquals(false, productPrice.isCurrent());
  }

  void whenAProductPriceHasNullDateFromAndNotNullDateTo_thenIsNotCurrent_test() {
    ProductPrice productPrice = ProductPrice.builder()
                                            .fromDate(null)
                                            .toDate(LocalDate.now())
                                            .build();
    assertEquals(false, productPrice.isCurrent());
  }

}
