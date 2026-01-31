package com.utopia_ok.epr_system.productComponent;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utopia_ok.epr_system.exceptions.ResourceNotFoundException;

@Service
public class ProductComponentService {

  private final ProductComponentRepository productComponentRepository;

  @Autowired
  public ProductComponentService(ProductComponentRepository productComponentRepository) {
    this.productComponentRepository = productComponentRepository;
  }

  public ProductComponent getProductComponentById(UUID id) {
    return this.productComponentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product component with id " + id + " not found"));
  }

  public ProductComponent createProductComponent(ProductComponent productComponent) {
    return this.productComponentRepository.save(productComponent);
  }

  public ProductComponent updateProductComponent(ProductComponent productComponent) {
    return this.productComponentRepository.save(productComponent);
  }

  public void deleteProductComponent(UUID id) {
    this.productComponentRepository.deleteById(id);
  }

  public List<ProductComponent> getAllProductComponents() {
    return this.productComponentRepository.findAll();
  }

}
