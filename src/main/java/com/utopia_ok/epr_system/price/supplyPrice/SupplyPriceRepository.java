package com.utopia_ok.epr_system.price.supplyPrice;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utopia_ok.epr_system.price.SupplyPrice;

@Repository
public interface SupplyPriceRepository extends JpaRepository<SupplyPrice, UUID> {
  
}
