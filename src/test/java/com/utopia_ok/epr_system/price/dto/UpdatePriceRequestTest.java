package com.utopia_ok.epr_system.price.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UpdatePriceRequestTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void aUpdatePriceRequestIsEqualToAnotherUpdatePriceRequestWhenAllFieldsAreEqual() {
    UpdatePriceRequest request1 = new UpdatePriceRequest(new BigInteger("123"), "2022-01-01");
    UpdatePriceRequest request2 = new UpdatePriceRequest(new BigInteger("123"), "2022-01-01");
    assertEquals(request1, request2);
  }

  @Test
  void aUpdatePriceRequestCanBeConvertedToString() {
    UpdatePriceRequest request = new UpdatePriceRequest(new BigInteger("123"), "2022-01-01");
    assertEquals("UpdatePriceRequest[priceCents=123, date=2022-01-01]", request.toString());
  }

  @Test
  void aUpdatePriceRequestHasThePriceCents() {
    UpdatePriceRequest request = new UpdatePriceRequest(new BigInteger("123"), "2022-01-01");
    assertEquals(new BigInteger("123"), request.priceCents());
  }

  @Test
  void aUpdatePriceRequestHasTheDate() {
    UpdatePriceRequest request = new UpdatePriceRequest(new BigInteger("123"), "2022-01-01");
    assertEquals("2022-01-01", request.date());
  }

  @Test
  void whenThePriceCentsIsNotPositiveThenConstraintViolationsAppears() {
    UpdatePriceRequest request = new UpdatePriceRequest(new BigInteger("-123"), "2022-01-01");
    Set<ConstraintViolation<UpdatePriceRequest>> violations = validator.validate(request);
    assertEquals(1, violations.size());
    assertEquals("Price must be positive", violations.iterator().next().getMessage());
  }

  @Test
  void whenThePriceCentsIsNullThenConstraintViolationsAppears() {
    UpdatePriceRequest request = new UpdatePriceRequest(null, "2022-01-01");
    Set<ConstraintViolation<UpdatePriceRequest>> violations = validator.validate(request);
    assertEquals(1, violations.size());
    assertEquals("Price is required", violations.iterator().next().getMessage());
  }

  @Test
  void whenTheDateIsBlankThenConstraintViolationsAppears() {
    UpdatePriceRequest request = new UpdatePriceRequest(new BigInteger("123"), "");
    Set<ConstraintViolation<UpdatePriceRequest>> violations = validator.validate(request);
    assertEquals(1, violations.size());
    assertEquals("Date is required", violations.iterator().next().getMessage());
  }
}
