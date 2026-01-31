package com.utopia_ok.epr_system.product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utopia_ok.epr_system.category.Category;
import com.utopia_ok.epr_system.price.productPrice.ProductPrice;
import com.utopia_ok.epr_system.product.dto.CreateProductRequest;
import com.utopia_ok.epr_system.product.dto.ProductResponse;
import com.utopia_ok.epr_system.productComponent.ProductComponent;
import com.utopia_ok.epr_system.supply.Supply;
import com.utopia_ok.epr_system.supply.SupplyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;
  private final SupplyService supplyService;

  @Autowired
  public ProductController(ProductService productService, SupplyService supplyService) {
    this.productService = productService;
    this.supplyService = supplyService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<ProductResponse>> getAllProducts() {
    return ResponseEntity.ok(this.productService.getAllProducts()
                         .stream()
                         .map(product -> toProductResponse(product)).toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID id) {
    Product product = this.productService.getProductById(id);
    return ResponseEntity.ok(toProductResponse(product));
  }

  @PostMapping("/create")
  public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
    // Create product
    Product product = toProductEntity(request);
    
    // Create product price
    ProductPrice productPrice = ProductPrice.builder()
                                            .priceCents(request.priceCents())
                                            .fromDate(LocalDate.now())
                                            .product(product)
                                            .build();
    // Add price to product
    product.addPrice(productPrice);
    // Create products components
    request.components().forEach(component -> {
      Supply supply = supplyService.getSupply(component.supplyId());
      ProductComponent productComponent = ProductComponent.builder()
                                                          .supply(supply)
                                                          .quantity(component.quantity())
                                                          .product(product)
                                                          .build();
      product.addComponent(productComponent);
    });
    // Save product
    productService.createProduct(product);
    return ResponseEntity.status(HttpStatus.CREATED).body(toProductResponse(product));
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
    this.productService.deleteProduct(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  private ProductResponse toProductResponse(Product product) {
    return new ProductResponse(
      product.getId(),
      product.getName(),
      product.getComponents().stream().map(productComponent -> productComponent.getSupply().getName()).toList(),
      product.getCurrentPrice().getPriceCents(),
      product.getCategories().stream().map(category -> category.name()).toList(),
      product.getSku());
  }
  
  private Product toProductEntity(CreateProductRequest request) {
    return Product.builder()
      .name(request.name())
      .prices(new ArrayList<ProductPrice>())
      .components(new ArrayList<ProductComponent>())
      .sku(request.sku())
      .categories(request.categoriesNames().stream().map(name -> Category.valueOf(name)).toList())
      .build();
  }
}
