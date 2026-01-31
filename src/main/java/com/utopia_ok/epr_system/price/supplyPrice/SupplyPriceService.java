package com.utopia_ok.epr_system.price.supplyPrice;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utopia_ok.epr_system.exceptions.ResourceNotFoundException;
import com.utopia_ok.epr_system.price.SupplyPrice;

@Service
public class SupplyPriceService {
  private final SupplyPriceRepository supplyPriceRepository;

  @Autowired
  public SupplyPriceService(SupplyPriceRepository supplyPriceRepository) {
    this.supplyPriceRepository = supplyPriceRepository;
  }

  public SupplyPrice createSupplyPrice(SupplyPrice supplyPrice) {
    return supplyPriceRepository.save(supplyPrice);
  }

  public SupplyPrice getSupplyPrice(UUID id) {
    return supplyPriceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supply price with id " + id + " not found"));
  }

  public SupplyPrice updateSupplyPrice(SupplyPrice supplyPrice) {
    return supplyPriceRepository.save(supplyPrice);
  }

  public void deleteSupplyPrice(UUID id) {
    supplyPriceRepository.deleteById(id);
  }

  public List<SupplyPrice> getAllSupplyPrices() {
    return supplyPriceRepository.findAll();
  }
}
