package com.utopia_ok.epr_system.price.supplyPrice;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.utopia_ok.epr_system.exceptions.ResourceNotFoundException;
import com.utopia_ok.epr_system.price.SupplyPrice;

public class SupplyPriceServiceTest {

  private SupplyPriceService supplyPriceService;
  private SupplyPriceRepository supplyPriceRepository;
  private UUID validId;
  private UUID invalidId;

  @BeforeEach
  void setUp() {
    validId = UUID.randomUUID();
    invalidId = UUID.randomUUID();
    supplyPriceRepository = Mockito.mock(SupplyPriceRepository.class);
    supplyPriceService = new SupplyPriceService(supplyPriceRepository);
  }

  @Test
  void whenSupplyPriceIsCreated_thenTheRepositoryIsCalledToSaveTheSupplyPrice() {
    SupplyPrice supplyPrice = new SupplyPrice();
    supplyPriceService.createSupplyPrice(supplyPrice);
    Mockito.verify(supplyPriceRepository, Mockito.times(1)).save(supplyPrice);
  }

  @Test
  void whenSupplyPriceIsDeleted_thenTheRepositoryIsCalledToDeleteTheSupplyPrice() {
    supplyPriceService.deleteSupplyPrice(validId);
    Mockito.verify(supplyPriceRepository, Mockito.times(1)).deleteById(validId);
  }

  @Test
  void whenSupplyPricesAreRetrieved_thenTheRepositoryIsCalledToGetAllSupplyPrices() {
    supplyPriceService.getAllSupplyPrices();
    Mockito.verify(supplyPriceRepository, Mockito.times(1)).findAll();
  }

  @Test
  void whenSupplyPriceIsRetrieved_thenTheRepositoryIsCalledToGetTheSupplyPrice() {
    Mockito.when(supplyPriceRepository.findById(validId)).thenReturn(Optional.of(new SupplyPrice()));
    supplyPriceService.getSupplyPrice(validId);
    Mockito.verify(supplyPriceRepository, Mockito.times(1)).findById(validId);
  }

  @Test
  void whenSupplyPriceIsUpdated_thenTheRepositoryIsCalledToUpdateTheSupplyPrice() {
    SupplyPrice supplyPrice = new SupplyPrice();
    supplyPriceService.updateSupplyPrice(supplyPrice);
    Mockito.verify(supplyPriceRepository, Mockito.times(1)).save(supplyPrice);
  }

  @Test
  void whenSupplyPriceIsNotFound_thenThrowException() {
    Mockito.when(supplyPriceRepository.findById(invalidId)).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> supplyPriceService.getSupplyPrice(invalidId));
  }
}
