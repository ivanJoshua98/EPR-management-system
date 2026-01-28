package com.utopia_ok.epr_system.contact.supplyProvider;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utopia_ok.epr_system.contact.SupplyProvider;
import com.utopia_ok.epr_system.contact.supplyProvider.dto.CreateSupplyProviderRequest;
import com.utopia_ok.epr_system.contact.supplyProvider.dto.SupplyProviderResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/supply-providers")
public class SupplyProviderController {

  private final SupplyProviderService supplyProviderService;

  @Autowired
  public SupplyProviderController(SupplyProviderService supplyProviderService) {
    this.supplyProviderService = supplyProviderService;
  }

  @PostMapping("/create")
  public ResponseEntity<SupplyProviderResponse> createSupplyProvider(@RequestBody @Valid CreateSupplyProviderRequest request) {
    SupplyProvider provider = supplyProviderService.createSupplyProvider(SupplyProvider.builder()
                                                                                       .name(request.name())
                                                                                       .phone(request.phone())
                                                                                       .page(request.page())
                                                                                       .build());
    return ResponseEntity.status(HttpStatus.CREATED)
                         .body(new SupplyProviderResponse(provider.getId(), 
                                                          provider.getName(), 
                                                          provider.getPhone(), 
                                                          provider.getPage()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<SupplyProviderResponse> getSupplyProvider(@PathVariable UUID id) {
    SupplyProvider provider = supplyProviderService.getSupplyProvider(id);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(new SupplyProviderResponse(provider.getId(), 
                                                          provider.getName(), 
                                                          provider.getPhone(), 
                                                          provider.getPage()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSupplyProvider(@PathVariable UUID id) {
    supplyProviderService.deleteSupplyProvider(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
                         .body(null);
  }

  @GetMapping("/all")
  public ResponseEntity<List<SupplyProviderResponse>> getAllSupplyProviders() {
    return ResponseEntity.status(HttpStatus.OK)
                         .body(supplyProviderService.getAllSupplyProviders()
                         .stream()
                         .map(provider -> new SupplyProviderResponse(provider.getId(), 
                                                                     provider.getName(), 
                                                                     provider.getPhone(), 
                                                                     provider.getPage()))
                         .collect(java.util.stream.Collectors.toList()));
  }
}
