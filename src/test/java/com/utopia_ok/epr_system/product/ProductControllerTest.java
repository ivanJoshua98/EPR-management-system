package com.utopia_ok.epr_system.product;

import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import com.utopia_ok.epr_system.category.Category;
import com.utopia_ok.epr_system.price.productPrice.ProductPrice;
import com.utopia_ok.epr_system.product.dto.CreateProductRequest;
import com.utopia_ok.epr_system.product.dto.ProductResponse;
import com.utopia_ok.epr_system.productComponent.ProductComponent;
import com.utopia_ok.epr_system.productComponent.dto.CreateProductComponentRequest;
import com.utopia_ok.epr_system.supply.SupplyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

@WebMvcTest(ProductController.class)
@AutoConfigureRestTestClient
public class ProductControllerTest {

  @Autowired
  private RestTestClient restTestClient;

  @MockitoBean
  private ProductService productService;

  @MockitoBean
  private SupplyService supplyService;

  private CreateProductRequest anyRequest;

  private Product anyProduct;

  private ProductPrice anyProductPrice;

  private UUID anyId;

  @BeforeEach
  void setUp () {
    anyRequest = new CreateProductRequest("anyName",
                                          new ArrayList<CreateProductComponentRequest>(),
                                          BigInteger.valueOf(10000),
                                          new ArrayList<String>(),
                                          "anySku");

    anyProductPrice = ProductPrice.builder()
                                  .priceCents(BigInteger.valueOf(10000))
                                  .fromDate(LocalDate.now())
                                  .product(anyProduct)
                                  .build();

    anyProduct = Product.builder()
                        .id(UUID.randomUUID())
                        .name("anyName")
                        .prices(new ArrayList<ProductPrice>(List.of(anyProductPrice)))
                        .categories(new ArrayList<Category>())
                        .components(new ArrayList<ProductComponent>())
                        .sku("anySku")
                        .build();
    anyProduct.addPrice(anyProductPrice);
  }

  @Test
  void whenCreateProduct_thenReturnCreatedStatus() {
    when(productService.createProduct(Mockito.isA(Product.class))).thenReturn(anyProduct);
    restTestClient.post().uri("/products/create").body(anyRequest)
                  .exchange()
                  .expectStatus().isCreated();
  }

  @Test
  void whenCreateProduct_thenReturnProductResponseSuccessfully() {
    when(productService.createProduct(Mockito.isA(Product.class))).thenReturn(anyProduct);
    restTestClient.post().uri("/products/create").body(anyRequest)
                  .exchange()
                  .expectBody(ProductResponse.class)
                  .isEqualTo(new ProductResponse( anyId,
                                                  anyProduct.getName(),
                                                  anyProduct.getComponents().stream().map(productComponent -> productComponent.getSupply().getName()).toList(),
                                                  anyProduct.getCurrentPrice().getPriceCents(),
                                                  anyProduct.getCategories().stream().map(category -> category.name()).toList(),
                                                  anyProduct.getSku()));
  }

  @Test
  void whenGetAllProducts_thenReturnOkStatus() {
    when(productService.getAllProducts()).thenReturn(List.of(anyProduct));
    restTestClient.get().uri("/products/all")
                  .exchange()
                  .expectStatus().isOk();
  }

  @Test
  void whenGetAllProducts_thenReturnListOfProductsSuccessfully() {
    when(productService.getAllProducts()).thenReturn(List.of(anyProduct));
    restTestClient.get().uri("/products/all")
                  .exchange()
                  .expectBody(List.class);
  }

  @Test
  void whenDeleteProduct_thenReturnNoContentStatus() {
    restTestClient.delete().uri("/products/{id}", anyProduct.getId())
                  .exchange()
                  .expectStatus().isNoContent();
  }

  @Test
  void whenGetProductById_thenReturnOkStatus() {
    when(productService.getProductById(anyProduct.getId())).thenReturn(anyProduct);
    restTestClient.get().uri("/products/{id}", anyProduct.getId())
                  .exchange()
                  .expectStatus().isOk();
  }

  @Test
  void whenGetProductById_thenReturnProductResponseSuccessfully() {
    when(productService.getProductById(anyProduct.getId())).thenReturn(anyProduct);
    restTestClient.get().uri("/products/{id}", anyProduct.getId())
                  .exchange()
                  .expectBody(ProductResponse.class)
                  .isEqualTo(new ProductResponse( anyProduct.getId(),
                                                  anyProduct.getName(),
                                                  anyProduct.getComponents().stream().map(productComponent -> productComponent.getSupply().getName()).toList(),
                                                  anyProduct.getCurrentPrice().getPriceCents(),
                                                  anyProduct.getCategories().stream().map(category -> category.name()).toList(),
                                                  anyProduct.getSku()));
  }
}
