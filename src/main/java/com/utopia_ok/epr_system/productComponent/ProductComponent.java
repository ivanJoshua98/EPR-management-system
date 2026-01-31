package com.utopia_ok.epr_system.productComponent;

import java.math.BigInteger;
import java.util.UUID;

import com.utopia_ok.epr_system.product.Product;
import com.utopia_ok.epr_system.supply.Supply;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class ProductComponent {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @ManyToOne
  @JoinColumn(name = "supply_id", referencedColumnName = "id")
  private Supply supply;
  private Integer quantity;
  @ManyToOne
  @JoinColumn(name = "product_id", referencedColumnName = "id")
  private Product product;

  @Builder
  public ProductComponent(UUID id, Supply supply, Integer quantity, Product product) {
    this.id = id;
    this.supply = supply;
    this.quantity = quantity;
    this.product = product;
  }

  public void addSupplyQuantity(Integer quantity) {
    this.quantity += quantity;
  }

  public void removeSupplyQuantity(Integer quantity) {
    this.quantity -= quantity;
  }

  public BigInteger getTotalCost() {
    return this.supply.getCurrentPrice().getPriceCents().multiply(BigInteger.valueOf(this.quantity));
  }
  
}
