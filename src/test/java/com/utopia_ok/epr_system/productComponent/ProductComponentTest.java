package com.utopia_ok.epr_system.productComponent;

import java.util.UUID;
import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.utopia_ok.epr_system.price.SupplyPrice;
import com.utopia_ok.epr_system.product.Product;
import com.utopia_ok.epr_system.supply.Supply;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProductComponentTest {

  private Supply anySupply;
  private Product anyProduct;
  private SupplyPrice anySupplyPrice;

  @BeforeEach
  void setUp() {
    anySupply = Mockito.mock(Supply.class);
    anyProduct = Mockito.mock(Product.class);
    anySupplyPrice = Mockito.mock(SupplyPrice.class);
  }


  @Test
  void whenAProductComponentIsCreatedWithId_thenTheIdIsNotNull() {
    UUID id = UUID.randomUUID();
    ProductComponent productComponent = ProductComponent.builder()
                                                        .id(id)
                                                        .build();
    assertEquals(id, productComponent.getId());
  }

  @Test
  void whenAProductComponentIsCreatedWithSupply_thenTheSupplyIsNotNull() {
    UUID id = UUID.randomUUID();
    ProductComponent productComponent = ProductComponent.builder()
    .id(id)
    .supply(anySupply)
    .build();

    assertEquals(anySupply, productComponent.getSupply());
  }

  @Test
  void whenAProductComponentIsCreatedWithQuantity_thenTheQuantityIsNotNull() {
    ProductComponent productComponent = ProductComponent.builder()
    .quantity(1)
    .build();

    assertEquals(1, productComponent.getQuantity());
  }

  @Test
  void whenAProductComponentIsCreatedWithProduct_thenTheProductIsNotNull() {
    ProductComponent productComponent = ProductComponent.builder()
    .product(anyProduct)
    .build();

    assertEquals(anyProduct, productComponent.getProduct());
  }

  @Test
  void whenAProductComponentHasSupplyAndQuantity_thenTheTotalCostIsNotNull() {
    when(anySupplyPrice.getPriceCents()).thenReturn(BigInteger.valueOf(1000));
    when(anySupply.getCurrentPrice()).thenReturn(anySupplyPrice);

    ProductComponent productComponent = ProductComponent.builder()
    .supply(anySupply)
    .quantity(2)
    .build();

    assertEquals(BigInteger.valueOf(2000), productComponent.getTotalCost());
  }

  @Test
  void whenAProductComponentRemovesQuantity_thenTheQuantityIsUpdated() {
    ProductComponent productComponent = ProductComponent.builder()
    .quantity(2)
    .build();

    productComponent.removeSupplyQuantity(1);

    assertEquals(1, productComponent.getQuantity());
  }

  @Test
  void whenAProductComponentAddsQuantity_thenTheQuantityIsUpdated() {
    ProductComponent productComponent = ProductComponent.builder()
    .quantity(2)
    .build(); 

    productComponent.addSupplyQuantity(1);

    assertEquals(3, productComponent.getQuantity());
  }
}
