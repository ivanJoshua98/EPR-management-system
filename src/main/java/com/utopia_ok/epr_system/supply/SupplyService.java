package com.utopia_ok.epr_system.supply;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplyService {
  
  private final SupplyRepository supplyRepository;

  @Autowired
  public SupplyService(SupplyRepository supplyRepository) {
    this.supplyRepository = supplyRepository;
  }

  public Supply createSupply(Supply supply) {
    return supplyRepository.save(supply);
  }

  public Supply getSupply(UUID id) {
    return supplyRepository.findById(id).orElse(null);
  }

  public Supply updateSupply(Supply supply) {
    return supplyRepository.save(supply);
  }

  public void deleteSupply(UUID id) {
    supplyRepository.deleteById(id);
  }

  public List<Supply> getAllSupplies() {
    return supplyRepository.findAll();
  }
}
