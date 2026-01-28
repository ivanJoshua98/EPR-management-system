package com.utopia_ok.epr_system.contact.supplyProvider;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.utopia_ok.epr_system.contact.SupplyProvider;
import com.utopia_ok.epr_system.exceptions.ResourseAlreadyExistsException;
import com.utopia_ok.epr_system.exceptions.ResourseNotFoundException;

public class SupplyProviderServiceTest {
  private SupplyProviderService supplyProviderService;
  private SupplyProviderRepository supplyProviderRepository;
  private UUID validId;
  private UUID invalidId;

  @BeforeEach
  void setUp() {
    validId = UUID.randomUUID();
    invalidId = UUID.randomUUID();
    supplyProviderRepository = Mockito.mock(SupplyProviderRepository.class);
    supplyProviderService = new SupplyProviderService(supplyProviderRepository);
  }

  @Test
  void whenSupplyProviderIsCreated_thenTheRepositoryIsCalledToCreateTheSupplyProvider() {
    SupplyProvider supplyProvider = new SupplyProvider();
    supplyProviderService.createSupplyProvider(supplyProvider);
    Mockito.verify(supplyProviderRepository, Mockito.times(1)).save(supplyProvider);
  }

  @Test
  void whenSupplyProviderIsCreatedWithExistingName_thenThrowException() {
    SupplyProvider supplyProvider = SupplyProvider.builder().name("Existing Name").build();
    Mockito.when(supplyProviderRepository.findByName(supplyProvider.getName())).thenReturn(Optional.of(supplyProvider));
    assertThrows(ResourseAlreadyExistsException.class, () -> supplyProviderService.createSupplyProvider(supplyProvider));
  }

  @Test
  void whenSupplyProviderIsRetrieved_thenTheRepositoryIsCalledToGetTheSupplyProvider() {
    Mockito.when(supplyProviderRepository.findById(validId)).thenReturn(Optional.of(new SupplyProvider()));
    supplyProviderService.getSupplyProvider(validId);
    Mockito.verify(supplyProviderRepository, Mockito.times(1)).findById(validId);
  }

  @Test
  void whenSupplyProviderIsNotFound_thenThrowException() {
    Mockito.when(supplyProviderRepository.findById(invalidId)).thenReturn(Optional.empty());
    assertThrows(ResourseNotFoundException.class, () -> supplyProviderService.getSupplyProvider(invalidId));
  }

  @Test
  void whenSupplyProviderIsUpdated_thenTheRepositoryIsCalledToUpdateTheSupplyProvider() {
    SupplyProvider supplyProvider = new SupplyProvider();
    supplyProviderService.updateSupplyProvider(supplyProvider);
    Mockito.verify(supplyProviderRepository, Mockito.times(1)).save(supplyProvider);
  }

  @Test
  void whenSupplyProviderIsDeleted_thenTheRepositoryIsCalledToDeleteTheSupplyProvider() {
    supplyProviderService.deleteSupplyProvider(validId);
    Mockito.verify(supplyProviderRepository, Mockito.times(1)).deleteById(validId);
  }

  @Test
  void whenSupplyProviderIsRetrievedByName_thenTheRepositoryIsCalledToGetTheSupplyProviderByName() {
    Mockito.when(supplyProviderRepository.findByName("Supply Provider Name")).thenReturn(Optional.of(new SupplyProvider()));
    supplyProviderService.getSupplyProviderByName("Supply Provider Name");
    Mockito.verify(supplyProviderRepository, Mockito.times(1)).findByName("Supply Provider Name");
  }

  @Test
  void whenSupplyProviderIsNotFoundByName_thenThrowException() {
    Mockito.when(supplyProviderRepository.findByName("Non-existent Name")).thenReturn(Optional.empty());
    assertThrows(ResourseNotFoundException.class, () -> supplyProviderService.getSupplyProviderByName("Non-existent Name"));
  }

  @Test
  void whenAllSupplyProvidersAreRetrieved_thenTheRepositoryIsCalledToGetAllSupplyProviders() {
    supplyProviderService.getAllSupplyProviders();
    Mockito.verify(supplyProviderRepository, Mockito.times(1)).findAll();
  }
}
