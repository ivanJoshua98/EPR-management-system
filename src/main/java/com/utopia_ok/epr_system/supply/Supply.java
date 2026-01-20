package com.utopia_ok.epr_system.supply;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.utopia_ok.epr_system.contact.SupplyProvider;
import com.utopia_ok.epr_system.price.SupplyPrice;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Supply {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String name;
  private List<SupplyPrice> prices;
  private SupplyProvider provider;
  private Integer stock;

  @Builder
  public Supply(UUID id, String name, List<SupplyPrice> prices, SupplyProvider provider, Integer stock) {
    this.id = id;
    this.name = name;
    this.prices = prices;
    this.provider = provider;
    this.stock = stock;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPrices(List<SupplyPrice> prices) {
    this.prices = prices;
  }

  public void setProvider(SupplyProvider provider) {
    this.provider = provider;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

  public void addStock(Integer i) {
    this.stock += i;
  }

  public void removeStock(Integer i) {
    this.stock -= i;
  }

  public SupplyPrice getCurrentPrice() {
    return this.prices.stream()
                      .filter(price -> price.isCurrent())
                      .findFirst()
                      .orElse(null);
  }

  // Closes the price. This method should be called when the price is no longer valid.
  // At this point, it should create a new price history.
  public void closePrice(Date toDate) {
    this.prices.stream()
               .filter(price -> price.isCurrent())
               .findFirst()
               .ifPresent(price -> price.close(toDate));
  }

  // Adds a new price to the supply.
  public void addPrice(SupplyPrice price) {
    this.prices.add(price);
  }

  // Updates the current price of the supply. 
  // Closes the previous price and creates a new one.
  public void updateCurrentPrice(Date date, Double value) {
    this.closePrice(date);
    SupplyPrice newPrice = SupplyPrice.builder()
                                      .value(value)
                                      .fromDate(date)
                                      .build();
    this.addPrice(newPrice);
  }
}
