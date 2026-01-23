package com.utopia_ok.epr_system.supply;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utopia_ok.epr_system.contact.SupplyProvider;
import com.utopia_ok.epr_system.contact.supplyProvider.SupplyProviderService;
import com.utopia_ok.epr_system.price.SupplyPrice;
import com.utopia_ok.epr_system.price.dto.UpdatePriceRequest;
import com.utopia_ok.epr_system.supply.dto.CreateSupplyRequest;
import com.utopia_ok.epr_system.supply.dto.SupplyResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/supplies")
public class SupplyController {
  
  private final SupplyService supplyService;
  private final SupplyProviderService supplyProviderService;

  @Autowired
  public SupplyController(SupplyService supplyService, SupplyProviderService supplyProviderService) {
    this.supplyService = supplyService;
    this.supplyProviderService = supplyProviderService;
  }

  @PostMapping
  public ResponseEntity<SupplyResponse> createSupply(@RequestBody @Valid CreateSupplyRequest supply) {
    SupplyProvider provider = supplyProviderService.getSupplyProviderByName(supply.providerName());
    Supply supplyToCreate = Supply.builder()
                                  .name(supply.name())
                                  .prices(new ArrayList<SupplyPrice>())
                                  .provider(provider)
                                  .stock(supply.stock())
                                  .build();
    SupplyPrice price =  SupplyPrice.builder()
                                    .priceCents(supply.priceCents())
                                    .fromDate(LocalDate.now())
                                    .supply(supplyToCreate)
                                    .build();
    supplyToCreate.addPrice(price);
    supplyService.createSupply(supplyToCreate);

    SupplyResponse supplyResponse = new SupplyResponse( supplyToCreate.getId(), 
                                                        supplyToCreate.getName(), 
                                                        supplyToCreate.getCurrentPrice().getPriceCents(), 
                                                        supplyToCreate.getProvider().getName(), 
                                                        supplyToCreate.getStock());
    return ResponseEntity.status(HttpStatus.CREATED).body(supplyResponse);
  }

  @GetMapping("/{id}")
  public ResponseEntity<SupplyResponse> getSupply(@PathVariable UUID id) {
    Supply supply = supplyService.getSupply(id); 
    SupplyResponse response = new SupplyResponse(supply.getId(),
                                                 supply.getName(),
                                                 supply.getCurrentPrice().getPriceCents(),
                                                 supply.getProvider().getName(),
                                                 supply.getStock());
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSupply(@PathVariable UUID id) {
    supplyService.deleteSupply(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @GetMapping
  public ResponseEntity<List<SupplyResponse>> getAllSupplies() {
    return ResponseEntity.status(HttpStatus.OK)
                         .body(supplyService.getAllSupplies()
                         .stream()
                         .map(supply -> new SupplyResponse(supply.getId(), 
                                                           supply.getName(), 
                                                           supply.getCurrentPrice().getPriceCents(), 
                                                           supply.getProvider().getName(), 
                                                           supply.getStock()))
                         .collect(Collectors.toList()));
  }

  @PutMapping("/{id}")
  public ResponseEntity<SupplyResponse> updateSupplyPrice(@PathVariable UUID id, @RequestBody @Valid UpdatePriceRequest request) {
    Supply supply = supplyService.getSupply(id);
    supply.updateCurrentPrice(LocalDate.parse(request.date()), request.priceCents());
    supplyService.updateSupply(supply);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(new SupplyResponse( supply.getId(), 
                                                   supply.getName(), 
                                                   supply.getCurrentPrice().getPriceCents(), 
                                                   supply.getProvider().getName(), 
                                                   supply.getStock()));
  }
}
