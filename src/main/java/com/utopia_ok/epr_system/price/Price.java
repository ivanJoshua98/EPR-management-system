package com.utopia_ok.epr_system.price;

import java.util.Date;
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
  private Double value;
  private Date fromDate;
  private Date toDate;

  public Boolean isCurrent() {
    return this.toDate == null && this.fromDate != null;
  }

  // Closes the price. This method should be called when the price is no longer valid.
  // At this point, it should create a new price history.
  public void close(Date toDate) {
    this.toDate = toDate; 
  }

}
