package com.utopia_ok.epr_system.supply.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class CreateSupplyRequestTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void aCreateSupplyRequestIsEqualToAnotherCreateSupplyRequestWhenAllFieldsAreEqual() {
    CreateSupplyRequest request1 = new CreateSupplyRequest("name", new BigInteger("123"), "providerName", 123);
    CreateSupplyRequest request2 = new CreateSupplyRequest("name", new BigInteger("123"), "providerName", 123);
    assertEquals(request1, request2);
  }

  @Test
  void aCreateSupplyRequestCanBeConvertedToString() {
    CreateSupplyRequest request = new CreateSupplyRequest("name", new BigInteger("123"), "providerName", 123);
    assertEquals("CreateSupplyRequest[name=name, priceCents=123, providerName=providerName, stock=123]", request.toString());
  }

  @Test
  void aCreateSupplyRequestHasTheName() {
    CreateSupplyRequest request = new CreateSupplyRequest("name", new BigInteger("123"), "providerName", 123);
    assertEquals("name", request.name());
  }

  @Test
  void aCreateSupplyRequestHasThePriceCents() {
    CreateSupplyRequest request = new CreateSupplyRequest("name", new BigInteger("123"), "providerName", 123);
    assertEquals(new BigInteger("123"), request.priceCents());
  }

  @Test
  void aCreateSupplyRequestHasTheProviderName() {
    CreateSupplyRequest request = new CreateSupplyRequest("name", new BigInteger("123"), "providerName", 123);
    assertEquals("providerName", request.providerName());
  }

  @Test
  void aCreateSupplyRequestHasTheStock() {
    CreateSupplyRequest request = new CreateSupplyRequest("name", new BigInteger("123"), "providerName", 123);
    assertEquals(123, request.stock());
  }

  @Test
  void whenTheNameIsBlankThenConstraintViolationsAppears() {
    CreateSupplyRequest request = new CreateSupplyRequest("", new BigInteger("123"), "providerName", 123);
    Set<ConstraintViolation<CreateSupplyRequest>> violations = validator.validate(request);
    assertEquals(1, violations.size());
    assertEquals("Name is required, cannot be blank", violations.iterator().next().getMessage());
  }

  @Test
  void whenThePriceCentsIsNotPositiveThenConstraintViolationsAppears() {
    CreateSupplyRequest request = new CreateSupplyRequest("name", new BigInteger("-123"), "providerName", 123);
    Set<ConstraintViolation<CreateSupplyRequest>> violations = validator.validate(request);
    assertEquals(1, violations.size());
    assertEquals("Price must be positive", violations.iterator().next().getMessage());
  }

  @Test
  void whenThePriceCentsIsNullThenConstraintViolationsAppears() {
    CreateSupplyRequest request = new CreateSupplyRequest("name", null, "providerName", 123);
    Set<ConstraintViolation<CreateSupplyRequest>> violations = validator.validate(request);
    assertEquals(1, violations.size());
    assertEquals("Price is required", violations.iterator().next().getMessage());
  }

  @Test
  void whenTheStockIsNotPositiveThenConstraintViolationsAppears() {
    CreateSupplyRequest request = new CreateSupplyRequest("name", new BigInteger("123"), "providerName", -123);
    Set<ConstraintViolation<CreateSupplyRequest>> violations = validator.validate(request);
    assertEquals(1, violations.size());
    assertEquals("Stock must be positive", violations.iterator().next().getMessage());
  }
}
