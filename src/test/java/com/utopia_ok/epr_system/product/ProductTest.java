package com.utopia_ok.epr_system.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.utopia_ok.epr_system.category.Category;
import com.utopia_ok.epr_system.price.productPrice.ProductPrice;
import com.utopia_ok.epr_system.productComponent.ProductComponent;

public class ProductTest {

  private UUID anyId;
  private ProductComponent anyComponent;
  private ProductPrice anyPrice;
  
  @BeforeEach
  void setUp() {
    anyId = UUID.randomUUID();
    anyComponent = Mockito.mock(ProductComponent.class);
    anyPrice = Mockito.mock(ProductPrice.class);
  }
  
  @Test
  void whenAProductIsCreatedWithId_thenIdIsNotNull_test() {
    Product product = Product.builder()
                             .id(anyId)
                             .build();
    assertEquals(anyId, product.getId());
  }

  @Test
  void whenAProductIsCreatedWithName_thenNameIsNotNull_test() {
    Product product = Product.builder()
                             .name("Test name")
                             .build();
    assertEquals("Test name", product.getName());
  }

  @Test
  void whenAProductIsCreatedWithComponents_thenComponentsIsNotNull_test() {
    Product product = Product.builder()
                             .components(List.of(anyComponent))
                             .build();
    assertEquals(List.of(anyComponent), product.getComponents());
  }

  @Test
  void whenAProductIsCreatedWithPrices_thenPricesIsNotNull_test() {
    Product product = Product.builder()
                             .prices(List.of(anyPrice))
                             .build();
    assertEquals(List.of(anyPrice), product.getPrices());
  }

  @Test
  void whenAProductIsCreatedWithCategories_thenCategoriesIsNotNull_test() {
    Product product = Product.builder()
                             .categories(List.of(Category.values()))
                             .build();
    assertEquals(List.of(Category.values()), product.getCategories());
  }

  @Test
  void whenAProductIsCreatedWithSku_thenSkuIsNotNull_test() {
    Product product = Product.builder()
                             .sku("Test sku")
                             .build();
    assertEquals("Test sku", product.getSku());
  }

  @Test 
  void whenAProductNotHasPrices_thenTheCurrentPriceIsNull_test() {
    Product product = Product.builder()
                             .prices(new ArrayList<ProductPrice>()) 
                             .build();
    assertEquals(null, product.getCurrentPrice());
  }

  @Test
  void whenTheCurrentPriceIsUpdated_thenThePriceChanges_test() {
    Product product = Product.builder()
                             .prices(new ArrayList<ProductPrice>())
                             .build();
    product.updateCurrentPrice(LocalDate.now(), BigInteger.valueOf(31500));
    assertEquals(BigInteger.valueOf(31500), product.getCurrentPrice().getPriceCents());
  }

  @Test
  void whenAComponentIsAdded_thenTheComponentsAreUpdated_test() {
    Product product = Product.builder()
                             .components(new ArrayList<ProductComponent>())
                             .build();
    product.addComponent(anyComponent);
    assertEquals(List.of(anyComponent), product.getComponents());
  }

  @Test
  void whenAComponentIsAdded_thenTheCostCanBeCalculated_test() {
    when(anyComponent.getTotalCost()).thenReturn(BigInteger.valueOf(10000));

    Product product = Product.builder()
                             .components(new ArrayList<ProductComponent>())
                             .build();
    product.addComponent(anyComponent);
    assertEquals(BigInteger.valueOf(10000), product.getTotalCost());
  }
}
