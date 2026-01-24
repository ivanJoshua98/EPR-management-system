package com.utopia_ok.epr_system.contact.supplyProvider.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class CreateSupplyProviderRequestTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();  
  }

  @Test
  void ASupplyProviderRequestIsEqualToAnotherSupplyProviderRequestWhenAllFieldsAreEqual() {
    CreateSupplyProviderRequest request1 = new CreateSupplyProviderRequest("name", 123, "page");
    CreateSupplyProviderRequest request2 = new CreateSupplyProviderRequest("name", 123, "page");
    assertEquals(request1, request2);
  }

  @Test
  void ASupplyProviderRequestIsNotEqualToAnotherSupplyProviderRequestWhenNameIsDifferent() {
    CreateSupplyProviderRequest request1 = new CreateSupplyProviderRequest("name", 123, "page");
    CreateSupplyProviderRequest request2 = new CreateSupplyProviderRequest("name2", 123, "page");
    assertNotEquals(request1, request2);
  }

  @Test
  void ASupplyProviderRequestIsNotEqualToAnotherSupplyProviderRequestWhenPhoneIsDifferent() {
    CreateSupplyProviderRequest request1 = new CreateSupplyProviderRequest("name", 123, "page");
    CreateSupplyProviderRequest request2 = new CreateSupplyProviderRequest("name", 1234, "page");
    assertNotEquals(request1, request2);
  }

  @Test
  void ASupplyProviderRequestIsNotEqualToAnotherSupplyProviderRequestWhenPageIsDifferent() {
    CreateSupplyProviderRequest request1 = new CreateSupplyProviderRequest("name", 123, "page");
    CreateSupplyProviderRequest request2 = new CreateSupplyProviderRequest("name", 123, "page2");
    assertNotEquals(request1, request2);

  }

  @Test
  void ASupplyProviderRequestHasThePhoneNumber() {
    CreateSupplyProviderRequest request = new CreateSupplyProviderRequest("name", 123, "page");
    assertEquals(123, request.phone());
  }

  @Test
  void ASupplyProviderRequestHasThePage() {
    CreateSupplyProviderRequest request = new CreateSupplyProviderRequest("name", 123, "page");
    assertEquals("page", request.page());
  }

  @Test
  void ASupplyProviderRequestHasTheName() {
    CreateSupplyProviderRequest request = new CreateSupplyProviderRequest("name", 123, "page");
    assertEquals("name", request.name());
  }

  @Test
  void ASupplyProviderRequestCanBeConvertedToString() {
    CreateSupplyProviderRequest request = new CreateSupplyProviderRequest("name", 123, "page");
    assertEquals("CreateSupplyProviderRequest[name=name, phone=123, page=page]", request.toString());
  }

  @Test
  void whenANameIsBlankThenConstraintViolationsAppears() {
    CreateSupplyProviderRequest request = new CreateSupplyProviderRequest("", 123, "page");
    Set<ConstraintViolation<CreateSupplyProviderRequest>> violations = validator.validate(request);
    assertEquals(1, violations.size());
    assertEquals("Name is required, cannot be blank", violations.iterator().next().getMessage());
  }

  @Test
  void whenANameNotIsBlankThenConstraintViolationsNotAppears() {
    CreateSupplyProviderRequest request = new CreateSupplyProviderRequest("name", 123, "page");
    Set<ConstraintViolation<CreateSupplyProviderRequest>> violations = validator.validate(request);
    assertEquals(0, violations.size());
  }

  @Test
  void aPhoneNumberCanBeNull() {
    CreateSupplyProviderRequest request = new CreateSupplyProviderRequest("name", null, "page");
    Set<ConstraintViolation<CreateSupplyProviderRequest>> violations = validator.validate(request);
    assertEquals(0, violations.size());
  }

  @Test
  void aPageCanBeNull() {
    CreateSupplyProviderRequest request = new CreateSupplyProviderRequest("name", 123, null);
    Set<ConstraintViolation<CreateSupplyProviderRequest>> violations = validator.validate(request);
    assertEquals(0, violations.size());
  }
}
