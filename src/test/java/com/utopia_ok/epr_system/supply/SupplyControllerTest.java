package com.utopia_ok.epr_system.supply;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
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
import com.utopia_ok.epr_system.contact.supplyProvider.SupplyProviderService;
import com.utopia_ok.epr_system.price.SupplyPrice;
import com.utopia_ok.epr_system.price.dto.UpdatePriceRequest;
import com.utopia_ok.epr_system.supply.dto.CreateSupplyRequest;
import com.utopia_ok.epr_system.supply.dto.SupplyResponse;

@WebMvcTest(SupplyController.class)
@AutoConfigureRestTestClient
public class SupplyControllerTest {

  @Autowired
  private RestTestClient restTestClient;

  @MockitoBean
  private SupplyService supplyService;

  @MockitoBean
  private SupplyProviderService supplyProviderService;

  private Supply anySupply;

  private CreateSupplyRequest anyRequest;

  private UpdatePriceRequest anyUpdateRequest;

  private SupplyProvider anySupplyProvider;

  private SupplyPrice anySupplyPrice;

  private UUID anyId;

  @BeforeEach
  void setUp () {
    anySupplyPrice = SupplyPrice.builder()
                                .id(UUID.randomUUID())
                                .priceCents(BigInteger.valueOf(10000))
                                .supply(anySupply)
                                .fromDate(LocalDate.now())
                                .build();

    anySupplyProvider = SupplyProvider.builder()
                                      .id(UUID.randomUUID())
                                      .name("anyProviderName")
                                      .phone(1111111111)
                                      .page("https://anyPage.com")
                                      .build();

    anySupply = Supply.builder()
                      .id(UUID.randomUUID())
                      .name("anyName")
                      .stock(10)
                      .prices(new ArrayList<SupplyPrice>())
                      .provider(anySupplyProvider)
                      .build();
    anySupply.addPrice(anySupplyPrice);

    anyRequest = new CreateSupplyRequest(anySupply.getName(),
                                         anySupply.getCurrentPrice().getPriceCents(),
                                         anySupply.getProvider().getName(),
                                         anySupply.getStock());

    anyUpdateRequest = new UpdatePriceRequest(anySupply.getCurrentPrice().getPriceCents(),
                                              LocalDate.now().toString());
  }

  @Test
  void whenCreateSupply_thenReturnCreatedStatus() throws Exception {
    when(supplyProviderService.getSupplyProviderByName(Mockito.isA(String.class))).thenReturn(anySupplyProvider);
    when(supplyService.createSupply(Mockito.isA(Supply.class))).thenReturn(anySupply);
    restTestClient.post().uri("/supplies/create").body(anyRequest)
                  .exchange()
                  .expectStatus().isCreated();
  }

  @Test
  void whenCreateSupply_thenReturnSupplyResponseSuccessfully() throws Exception {
    when(supplyProviderService.getSupplyProviderByName(Mockito.isA(String.class))).thenReturn(anySupplyProvider);
    when(supplyService.createSupply(Mockito.isA(Supply.class))).thenReturn(anySupply);
    restTestClient.post().uri("/supplies/create").body(anyRequest)
                  .exchange()
                  .expectBody(SupplyResponse.class)
                  .isEqualTo(new SupplyResponse(anyId, 
                                                anySupply.getName(),
                                                anySupply.getCurrentPrice().getPriceCents(), 
                                                anySupply.getProvider().getName(),
                                                anySupply.getStock()));
  }

  @Test
  void whenDeleteSupply_thenReturnNoContentStatus() throws Exception {
    UUID id = UUID.randomUUID();
    restTestClient.delete().uri("/supplies/{id}", id.toString())
                  .exchange()
                  .expectStatus().isNoContent();

    verify(supplyService, times(1)).deleteSupply(id);
  }

  @Test
  void whenGetAllSupplies_thenReturnOkStatus() throws Exception {
    when(supplyService.getAllSupplies()).thenReturn(List.of(anySupply));
    restTestClient.get().uri("/supplies/all")
                  .exchange()
                  .expectStatus().isOk();
  }

  @Test
  void whenGetAllSupplies_thenReturnSupplyResponseSuccessfully() throws Exception {
    when(supplyService.getAllSupplies()).thenReturn(List.of(anySupply));
    restTestClient.get().uri("/supplies/all")
                  .exchange()
                  .expectBody(List.class);
  }

  @Test
  void whenGetSupply_thenReturnOkStatus() throws Exception {
    UUID anyId = UUID.randomUUID();
    when(supplyService.getSupply(anyId)).thenReturn(anySupply);
    restTestClient.get().uri("/supplies/{id}", anyId)
                  .exchange()
                  .expectStatus().isOk();
  }

  @Test
  void whenGetSupply_thenReturnSupplyResponseSuccessfully() throws Exception {
    UUID anyId = UUID.randomUUID();
    when(supplyService.getSupply(anyId)).thenReturn(anySupply);
    restTestClient.get().uri("/supplies/{id}", anyId)
                  .exchange()
                  .expectBody(SupplyResponse.class)
                  .isEqualTo(new SupplyResponse(anySupply.getId(), 
                                                anySupply.getName(),
                                                anySupply.getCurrentPrice().getPriceCents(), 
                                                anySupply.getProvider().getName(),
                                                anySupply.getStock()));
  }

  @Test
  void whenUpdateSupplyPrice_thenReturnOkStatus() throws Exception {
    when(supplyService.getSupply(anySupply.getId())).thenReturn(anySupply);
    when(supplyService.updateSupply(Mockito.isA(Supply.class))).thenReturn(anySupply);
    restTestClient.put().uri("/supplies/{id}/update-price", anySupply.getId())
                  .body(anyUpdateRequest)
                  .exchange()
                  .expectStatus().isOk();
  }

  @Test
  void whenUpdateSupplyPrice_thenReturnSupplyResponseSuccessfully() throws Exception {
    when(supplyService.getSupply(anySupply.getId())).thenReturn(anySupply);
    when(supplyService.updateSupply(Mockito.isA(Supply.class))).thenReturn(anySupply);
    restTestClient.put().uri("/supplies/{id}/update-price", anySupply.getId()).body(anyUpdateRequest)
                  .exchange()
                  .expectBody(SupplyResponse.class)
                  .isEqualTo(new SupplyResponse(anySupply.getId(), 
                                                anySupply.getName(),
                                                anySupply.getCurrentPrice().getPriceCents(), 
                                                anySupply.getProvider().getName(),
                                                anySupply.getStock()));
  }
}
