package com.utopia_ok.epr_system.price;

import java.util.Date;
import java.util.UUID;

import com.utopia_ok.epr_system.supply.Supply;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SupplyPrice extends Price {

  private Supply supply;

  @Builder
  public SupplyPrice(UUID id, Double value, Date fromDate, Date toDate, Supply supply) {
    super(id, value, fromDate, toDate);
    this.supply = supply;
  }
}
