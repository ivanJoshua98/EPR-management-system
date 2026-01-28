package com.utopia_ok.epr_system.contact.supplyProvider;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utopia_ok.epr_system.contact.SupplyProvider;

@Repository
public interface SupplyProviderRepository extends JpaRepository<SupplyProvider, UUID> {

  public Optional<SupplyProvider> findByName(String name);
}
