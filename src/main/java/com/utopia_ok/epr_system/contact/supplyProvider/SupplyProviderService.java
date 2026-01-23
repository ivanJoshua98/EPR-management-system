package com.utopia_ok.epr_system.contact.supplyProvider;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utopia_ok.epr_system.contact.SupplyProvider;
import com.utopia_ok.epr_system.exceptions.ResourseAlreadyExistsException;
import com.utopia_ok.epr_system.exceptions.ResourseNotFoundException;

@Service
public class SupplyProviderService {
  private final SupplyProviderRepository supplyProviderRepository;

  @Autowired
  public SupplyProviderService(SupplyProviderRepository supplyProviderRepository) {
    this.supplyProviderRepository = supplyProviderRepository;
  }

  public SupplyProvider createSupplyProvider(SupplyProvider supplyProvider) {
    try {
      getSupplyProviderByName(supplyProvider.getName());
      throw new ResourseAlreadyExistsException("Supply provider with name " + supplyProvider.getName() + " already exists");
    } catch (ResourseNotFoundException e) {
      return supplyProviderRepository.save(supplyProvider); 
    }
  }

  public SupplyProvider getSupplyProvider(UUID id) {
    return supplyProviderRepository.findById(id).orElseThrow(() -> new ResourseNotFoundException("Supply provider with id " + id + " not found"));
  }

  public SupplyProvider getSupplyProviderByName(String name) {
    return supplyProviderRepository.findByName(name).orElseThrow(() -> new ResourseNotFoundException("Supply provider with name " + name + " not found"));
  }

  public SupplyProvider updateSupplyProvider(SupplyProvider supplyProvider) {
    return supplyProviderRepository.save(supplyProvider);
  }

  public void deleteSupplyProvider(UUID id) {
    supplyProviderRepository.deleteById(id);
  }

  public List<SupplyProvider> getAllSupplyProviders() {
    return supplyProviderRepository.findAll();
  }
}
