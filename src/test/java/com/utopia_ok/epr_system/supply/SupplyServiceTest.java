package com.utopia_ok.epr_system.supply;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.utopia_ok.epr_system.exceptions.ResourseAlreadyExistsException;
import com.utopia_ok.epr_system.exceptions.ResourseNotFoundException;

public class SupplyServiceTest {

  private SupplyService supplyService;
  private SupplyRepository supplyRepository;
  private UUID validId;
  private UUID invalidId;

  @BeforeEach
  void setUp() {
    validId = UUID.randomUUID();
    invalidId = UUID.randomUUID();
    supplyRepository = Mockito.mock(SupplyRepository.class);
    supplyService = new SupplyService(supplyRepository);
  }

  @Test
  void whenSupplyIsCreated_thenTheRepositoryIsCalledToCreateTheSupply() {
    Supply supply = new Supply();
    supplyService.createSupply(supply);
    Mockito.verify(supplyRepository, Mockito.times(1)).save(supply);
  }

  @Test
  void whenSupplyIsCreatedWithExistingName_thenThrowException() {
    Supply supply = Supply.builder().name("Existing Name").build();
    Mockito.when(supplyRepository.findByName("Existing Name")).thenReturn(Optional.of(supply));
    assertThrows(ResourseAlreadyExistsException.class, () -> supplyService.createSupply(supply));
  }

  @Test
  void whenSupplyIsRetrieved_thenTheRepositoryIsCalledToGetTheSupply() {
    Mockito.when(supplyRepository.findById(validId)).thenReturn(Optional.of(new Supply()));
    supplyService.getSupply(validId);
    Mockito.verify(supplyRepository, Mockito.times(1)).findById(validId);
  }

  @Test
  void whenSupplyIsNotFound_thenThrowException() {
    Mockito.when(supplyRepository.findById(invalidId)).thenReturn(Optional.empty());
    assertThrows(ResourseNotFoundException.class, () -> supplyService.getSupply(invalidId));
  }

  @Test
  void whenSupplyIsUpdated_thenTheRepositoryIsCalledToUpdateTheSupply() {
    Supply supply = new Supply();
    supplyService.updateSupply(supply);
    Mockito.verify(supplyRepository, Mockito.times(1)).save(supply);
  }

  @Test
  void whenSupplyIsDeleted_thenTheRepositoryIsCalledToDeleteTheSupply() {
    supplyService.deleteSupply(validId);
    Mockito.verify(supplyRepository, Mockito.times(1)).deleteById(validId);
  }

  @Test
  void whenAllSuppliesAreRetrieved_thenTheRepositoryIsCalledToGetAllSupplies() {
    supplyService.getAllSupplies();
    Mockito.verify(supplyRepository, Mockito.times(1)).findAll();
  }

  @Test
  void whenSupplyIsRetrievedByName_thenTheRepositoryIsCalledToGetTheSupplyByName() {
    Mockito.when(supplyRepository.findByName("Supply Name")).thenReturn(Optional.of(new Supply()));
    supplyService.getSupplyByName("Supply Name");
    Mockito.verify(supplyRepository, Mockito.times(1)).findByName("Supply Name");
  }

  @Test
  void whenSupplyIsNotFoundByName_thenThrowException() {
    Mockito.when(supplyRepository.findByName("Non-existent Name")).thenReturn(Optional.empty());
    assertThrows(ResourseNotFoundException.class, () -> supplyService.getSupplyByName("Non-existent Name"));
  }
}
