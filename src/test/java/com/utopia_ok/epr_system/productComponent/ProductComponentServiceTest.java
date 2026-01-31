package com.utopia_ok.epr_system.productComponent;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.utopia_ok.epr_system.exceptions.ResourceNotFoundException;

public class ProductComponentServiceTest {

  private ProductComponentService productComponentService;

  private ProductComponentRepository productComponentRepository;

  private ProductComponent anyProductComponent;

  private UUID id;

  @BeforeEach
  void setUp() {
    productComponentRepository = Mockito.mock(ProductComponentRepository.class);
    productComponentService = new ProductComponentService(productComponentRepository);
    anyProductComponent = Mockito.mock(ProductComponent.class);
    id = UUID.randomUUID();
  }

  @Test
  void whenAProductComponentIsCreated_thenTheRepositoryIsCalledToCreateTheProductComponent() {
    when(productComponentRepository.save(anyProductComponent)).thenReturn(anyProductComponent);
    productComponentService.createProductComponent(anyProductComponent);
    Mockito.verify(productComponentRepository, Mockito.times(1)).save(anyProductComponent);
  }

  @Test
  void whenAProductComponentIsDeleted_thenTheRepositoryIsCalledToDeleteTheProductComponent() {
    when(productComponentRepository.findById(id)).thenReturn(Optional.of(anyProductComponent));
    productComponentService.deleteProductComponent(id);
    Mockito.verify(productComponentRepository, Mockito.times(1)).deleteById(id);
  }

  @Test
  void whenAllProductComponentsAreRetrieved_thenTheRepositoryIsCalledToGetAllProductComponents() {
    when(productComponentRepository.findAll()).thenReturn(List.of(anyProductComponent));
    productComponentService.getAllProductComponents();
    Mockito.verify(productComponentRepository, Mockito.times(1)).findAll();
  }

  @Test
  void whenAProductComponentIsRetrievedById_thenTheRepositoryIsCalledToGetTheProductComponent() {
    when(productComponentRepository.findById(id)).thenReturn(Optional.of(anyProductComponent));
    productComponentService.getProductComponentById(id);
    Mockito.verify(productComponentRepository, Mockito.times(1)).findById(id);
  }

  @Test
  void whenAProductComponentNotFound_thenThrowException() {
    when(productComponentRepository.findById(id)).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> productComponentService.getProductComponentById(id));
  }

  @Test
  void whenAProductComponentIsUpdated_thenTheRepositoryIsCalledToUpdateTheProductComponent() {
    when(productComponentRepository.save(anyProductComponent)).thenReturn(anyProductComponent);
    productComponentService.updateProductComponent(anyProductComponent);
    Mockito.verify(productComponentRepository, Mockito.times(1)).save(anyProductComponent);
  }
}
