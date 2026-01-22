package com.utopia_ok.epr_system.contact.supplyProvider;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/supply-providers")
public class SupplyProviderController {

  private final SupplyProviderService supplyProviderService;

  @Autowired
  public SupplyProviderController(SupplyProviderService supplyProviderService) {
    this.supplyProviderService = supplyProviderService;
  }

  @PostMapping
  public SupplyProviderResponse createSupplyProvider(@RequestBody CreateSupplyProviderRequest request) {
    SupplyProvider provider = supplyProviderService.createSupplyProvider(SupplyProvider.builder()
                                                                                       .name(request.name())
                                                                                       .phone(request.phone())
                                                                                       .page(request.page())
                                                                                       .build());
    return new SupplyProviderResponse(provider.getId(), provider.getName(), provider.getPhone(), provider.getPage());
  }

  @GetMapping("/{id}")
  public SupplyProviderResponse getSupplyProvider(@PathVariable UUID id) {
    SupplyProvider provider = supplyProviderService.getSupplyProvider(id);
    return new SupplyProviderResponse(provider.getId(), provider.getName(), provider.getPhone(), provider.getPage());
  }

  @DeleteMapping("/{id}")
  public void deleteSupplyProvider(@PathVariable UUID id) {
    supplyProviderService.deleteSupplyProvider(id);
  }

  @GetMapping
  public List<SupplyProviderResponse> getAllSupplyProviders() {
    return supplyProviderService.getAllSupplyProviders()
        .stream()
        .map(provider -> new SupplyProviderResponse(provider.getId(), provider.getName(), provider.getPhone(), provider.getPage()))
        .collect(java.util.stream.Collectors.toList());
  }
}
