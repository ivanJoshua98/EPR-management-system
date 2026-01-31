package com.utopia_ok.epr_system.price.productPrice;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

import com.utopia_ok.epr_system.price.Price;
import com.utopia_ok.epr_system.product.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class ProductPrice extends Price{
  
  @ManyToOne
  @JoinColumn(name = "product_id", referencedColumnName = "id")
  private Product product;

  @Builder
  public ProductPrice(UUID id, BigInteger priceCents, LocalDate fromDate, LocalDate toDate, Product product) {
    super(id, priceCents, fromDate, toDate);
    this.product = product;
  }

}
