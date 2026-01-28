package com.utopia_ok.epr_system.price;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

import com.utopia_ok.epr_system.supply.Supply;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class SupplyPrice extends Price {

  @ManyToOne
  @JoinColumn(name = "supply_id", referencedColumnName = "id")
  private Supply supply;

  @Builder
  public SupplyPrice(UUID id, BigInteger priceCents, LocalDate fromDate, LocalDate toDate, Supply supply) {
    super(id, priceCents, fromDate, toDate);
    this.supply = supply;
  }
}
