package com.utopia_ok.epr_system.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ContactTest {

  private UUID anyId;
  private String anyName;
  private Integer anyPhone;

  @BeforeEach
  void setUp() {
    anyId = UUID.randomUUID();
    anyName = "anyName";
    anyPhone = 123456789;
  }

  @Test
  void whenAContactIsCreatedWithId_thenIdIsNotNull_test() {
    Contact contact = new Contact(anyId, anyName, anyPhone);
    assertEquals(anyId, contact.getId());
  }

  @Test
  void whenAContactIsCreatedWithName_thenNameIsNotNull_test() {
    Contact contact = new Contact(anyId, anyName, anyPhone);
    assertEquals(anyName, contact.getName());
  }

  @Test
  void whenAContactIsCreatedWithPhone_thenPhoneIsNotNull_test() {
    Contact contact = new Contact(anyId, anyName, anyPhone);
    assertEquals(anyPhone, contact.getPhone());
  }
}
