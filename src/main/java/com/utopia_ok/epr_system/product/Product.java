package com.utopia_ok.epr_system.product;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.utopia_ok.epr_system.productComponent.ProductComponent;
import com.utopia_ok.epr_system.price.productPrice.ProductPrice;
import com.utopia_ok.epr_system.category.Category;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String name;
  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProductComponent> components;
  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProductPrice> prices;
  @ElementCollection(targetClass = Category.class)
  @CollectionTable(name = "product_categories", joinColumns = @JoinColumn(name = "product_id"))
  @Column(name = "category")
  @Enumerated(EnumType.STRING)
  private List<Category> categories;
  private String sku;

  @Builder
  public Product(UUID id, String name, List<ProductComponent> components, List<ProductPrice> prices,
      List<Category> categories, String sku) {
    this.id = id;
    this.name = name;
    this.components = components;
    this.prices = prices;
    this.categories = categories;
    this.sku = sku;
  }

  public ProductPrice getCurrentPrice() {
    return this.prices.stream().filter(price -> price.isCurrent()).findFirst().orElse(null);
  }

  // Closes the price. This method should be called when the price is no longer valid.
  // At this point, it should create a new price history.
  public void closePrice(LocalDate toDate) {
    this.prices.stream()
               .filter(price -> price.isCurrent())
               .findFirst()
               .ifPresent(price -> price.close(toDate));
  }

  public void addPrice(ProductPrice price) {
    this.prices.add(price);
  }

  // Updates the current price of the supply. 
  // Closes the previous price and creates a new one.
  public void updateCurrentPrice(LocalDate date, BigInteger value) {
    this.closePrice(date);
    ProductPrice newPrice = ProductPrice.builder()
                                      .priceCents(value)
                                      .fromDate(date)
                                      .product(this)
                                      .build();
    this.addPrice(newPrice);
  }

  public BigInteger getTotalCost() {
    return this.components.stream().map(ProductComponent::getTotalCost).reduce(BigInteger.ZERO, BigInteger::add);
  }

  public void addComponent(ProductComponent component) {
    this.components.add(component);
  }
}
