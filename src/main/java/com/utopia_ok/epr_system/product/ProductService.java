package com.utopia_ok.epr_system.product;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utopia_ok.epr_system.exceptions.ResourceAlreadyExistsException;
import com.utopia_ok.epr_system.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product getProductById(UUID id) {
    return this.productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
  }

  public Product createProduct(Product product) {
    try {
      this.getProductByName(product.getName());
    } catch (ResourceNotFoundException e) {
      return this.productRepository.save(product);
    }
    throw new ResourceAlreadyExistsException("Product with name " + product.getName() + " already exists");
  }

  public Product updateProduct(Product product) {
    return this.productRepository.save(product);
  }

  public void deleteProduct(UUID id) {
    this.productRepository.deleteById(id);
  }

  public List<Product> getAllProducts() {
    return this.productRepository.findAll();
  }

  public Product getProductByName(String name) {
    return this.productRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Product with name " + name + " not found"));
  }
  
}
