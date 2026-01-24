package com.utopia_ok.epr_system.contact.supplyProvider;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import com.utopia_ok.epr_system.contact.SupplyProvider;
import com.utopia_ok.epr_system.contact.supplyProvider.dto.CreateSupplyProviderRequest;
import com.utopia_ok.epr_system.contact.supplyProvider.dto.SupplyProviderResponse;

@WebMvcTest(SupplyProviderController.class)
@AutoConfigureRestTestClient
public class SupplyProviderControllerTest {

  @Autowired
  private RestTestClient restTestClient;

  @MockitoBean
  private SupplyProviderService supplyProviderService;

  private SupplyProvider anySupplyProvider;

  private CreateSupplyProviderRequest anyRequest;

  @BeforeEach
  public void setUp() {
    anySupplyProvider = Mockito.mock(SupplyProvider.class);
    when(anySupplyProvider.getId()).thenReturn(UUID.randomUUID());
    when(anySupplyProvider.getName()).thenReturn("anyName");
    when(anySupplyProvider.getPhone()).thenReturn(11111111);
    when(anySupplyProvider.getPage()).thenReturn("anyPage");

    anyRequest = Mockito.mock(CreateSupplyProviderRequest.class);
    when(anyRequest.name()).thenReturn("anyName");
    when(anyRequest.phone()).thenReturn(11111111);
    when(anyRequest.page()).thenReturn("anyPage");
  }

  @Test
  void whenCreateSupplyProvider_thenReturnCreatedStatus() throws Exception {
    when(supplyProviderService.createSupplyProvider(Mockito.isA(SupplyProvider.class))).thenReturn(anySupplyProvider);
    restTestClient.post().uri("/supply-providers/create").body(anyRequest)
                  .exchange()
                  .expectStatus().isCreated();
  }

  @Test
  void whenCreateSupplyProvider_thenReturnSupplyProviderResponseSuccessfully() throws Exception {
    when(supplyProviderService.createSupplyProvider(Mockito.isA(SupplyProvider.class))).thenReturn(anySupplyProvider);
    restTestClient.post().uri("/supply-providers/create").body(anyRequest)
                  .exchange()
                  .expectBody(SupplyProviderResponse.class)
                  .isEqualTo(new SupplyProviderResponse(anySupplyProvider.getId(), 
                                                        anySupplyProvider.getName(), 
                                                        anySupplyProvider.getPhone(), 
                                                        anySupplyProvider.getPage()));
  }

  @Test
  void whenGetAllSupplyProviders_thenReturnOkStatus() {
    when(supplyProviderService.getAllSupplyProviders()).thenReturn(List.of(anySupplyProvider));
    restTestClient.get().uri("/supply-providers/all")
                  .exchange()
                  .expectStatus().isOk();
  }

  @Test
  void whenGetAllSupplyProviders_thenReturnSupplyProviderResponseSuccessfully() {
    when(supplyProviderService.getAllSupplyProviders()).thenReturn(List.of(anySupplyProvider));
    restTestClient.get().uri("/supply-providers/all")
                  .exchange()
                  .expectBody(List.class);
  }

  @Test
  void whenGetSupplyProvider_thenReturnOkStatus() {
    UUID anyId = UUID.randomUUID();
    when(supplyProviderService.getSupplyProvider(anyId)).thenReturn(anySupplyProvider);
    restTestClient.get().uri("/supply-providers/{id}", anyId)
                  .exchange()
                  .expectStatus().isOk();
  }

  @Test
  void whenGetSupplyProvider_thenReturnSupplyProviderResponseSuccessfully() {
    UUID anyId = UUID.randomUUID();
    when(supplyProviderService.getSupplyProvider(anyId)).thenReturn(anySupplyProvider);
    restTestClient.get().uri("/supply-providers/{id}", anyId)
                  .exchange()
                  .expectBody(SupplyProviderResponse.class)
                  .isEqualTo(new SupplyProviderResponse(anySupplyProvider.getId(), 
                                                        anySupplyProvider.getName(), 
                                                        anySupplyProvider.getPhone(), 
                                                        anySupplyProvider.getPage()));
  }

  @Test
  void whenDeleteSupplyProvider_thenReturnNoContentStatus() {
    UUID anyId = UUID.randomUUID();
    restTestClient.delete().uri("/supply-providers/{id}", anyId)
                  .exchange()
                  .expectStatus().isNoContent();

    verify(supplyProviderService, times(1)).deleteSupplyProvider(anyId);
  }
}
