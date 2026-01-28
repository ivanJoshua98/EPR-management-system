package com.utopia_ok.epr_system.contact;

import java.util.UUID;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class SupplyProvider extends Contact {
  private String page;

  @Builder
  public SupplyProvider(UUID id, String name, Integer phone, String page) {
    super(id, name, phone);
    this.page = page;
  }
}


