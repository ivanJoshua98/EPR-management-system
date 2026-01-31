package com.utopia_ok.epr_system.product;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.utopia_ok.epr_system.exceptions.ResourceAlreadyExistsException;
import com.utopia_ok.epr_system.exceptions.ResourceNotFoundException;

public class ProductServiceTest {

  private ProductService productService;
  private ProductRepository productRepository;
  private UUID validId;
  private UUID invalidId;

  @BeforeEach
  void setUp() {
    validId = UUID.randomUUID();
    invalidId = UUID.randomUUID();
    productRepository = Mockito.mock(ProductRepository.class);
    productService = new ProductService(productRepository);
  }

  @Test
  void whenProductIsCreated_thenTheRepositoryIsCalledToCreateTheProduct() {
    Product product = new Product();
    productService.createProduct(product);
    Mockito.verify(productRepository, Mockito.times(1)).save(product);
  }

  @Test
  void whenAProductIsCreatedWithExistingName_thenThrowException() {
    Product product = Product.builder().name("Existing Name").build();
    Mockito.when(productRepository.findByName("Existing Name")).thenReturn(Optional.of(product));
    assertThrows(ResourceAlreadyExistsException.class, () -> productService.createProduct(product));
  }

  @Test
  void whenAProductIsRetrieved_thenTheRepositoryIsCalledToGetTheProduct() {
    Mockito.when(productRepository.findById(validId)).thenReturn(Optional.of(new Product()));
    productService.getProductById(validId);
    Mockito.verify(productRepository, Mockito.times(1)).findById(validId);
  }

  @Test
  void whenAProductNotFoundById_thenThrowException() {
    Mockito.when(productRepository.findById(invalidId)).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(invalidId));
  }

  @Test
  void whenAProductIsRetrievedByName_thenTheRepositoryIsCalledToGetTheProduct() {
    Mockito.when(productRepository.findByName("Existing Name")).thenReturn(Optional.of(new Product()));
    productService.getProductByName("Existing Name");
    Mockito.verify(productRepository, Mockito.times(1)).findByName("Existing Name");
  }

  @Test
  void whenAProductNotFoundByName_thenThrowException() {
    Mockito.when(productRepository.findByName("Non Existing Name")).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> productService.getProductByName("Non Existing Name"));
  }

  @Test
  void whenAProductIsUpdated_thenTheRepositoryIsCalledToUpdateTheProduct() {
    Product product = new Product();
    productService.updateProduct(product);
    Mockito.verify(productRepository, Mockito.times(1)).save(product);
  }

  @Test
  void whenAProductIsDeleted_thenTheRepositoryIsCalledToDeleteTheProduct() {
    productService.deleteProduct(validId);
    Mockito.verify(productRepository, Mockito.times(1)).deleteById(validId);
  }

  @Test
  void whenAllProductsAreRetrieved_thenTheRepositoryIsCalledToGetAllProducts() {
    productService.getAllProducts();
    Mockito.verify(productRepository, Mockito.times(1)).findAll();
  }
}
