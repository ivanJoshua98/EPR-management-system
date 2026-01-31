package com.utopia_ok.epr_system.supply;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utopia_ok.epr_system.exceptions.ResourceAlreadyExistsException;
import com.utopia_ok.epr_system.exceptions.ResourceNotFoundException;

@Service
public class SupplyService {
  
  private final SupplyRepository supplyRepository;

  @Autowired
  public SupplyService(SupplyRepository supplyRepository) {
    this.supplyRepository = supplyRepository;
  }

  public Supply createSupply(Supply supply) {
    try {
      getSupplyByName(supply.getName());
      throw new ResourceAlreadyExistsException("Supply with name " + supply.getName() + " already exists");
    } catch (ResourceNotFoundException e) {
      return supplyRepository.save(supply);
    }
  }

  public Supply getSupplyByName(String name) {
    return supplyRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Supply with name " + name + " not found"));
  }

  public Supply getSupply(UUID id) {
    return supplyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supply with id " + id + " not found"));
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
