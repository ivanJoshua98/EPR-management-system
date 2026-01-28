package com.utopia_ok.epr_system.price;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Getter
public abstract class Price {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private BigInteger priceCents;
  private LocalDate fromDate;
  private LocalDate toDate;

  public Boolean isCurrent() {
    return this.toDate == null && this.fromDate != null;
  }

  // Closes the price. This method should be called when the price is no longer valid.
  // At this point, it should create a new price history.
  public void close(LocalDate toDate) {
    this.toDate = toDate; 
  }

}
