package com.utopia_ok.epr_system.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CategoryTest {

  @Test
  void theInvalidValueShouldThrowIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> Category.valueOf("INVALID"));
  }

  @Test
  void theValueBIRTHDAYShouldReturnExistingEnum() {
    Category category = Category.valueOf("BIRTHDAY");
    assertEquals(Category.BIRTHDAY, category);
  }

  @Test
  void theValueKINDER_SCHOOLShouldReturnExistingEnum() {
    Category category = Category.valueOf("KINDER_SCHOOL");
    assertEquals(Category.KINDER_SCHOOL, category);
  }

  @Test
  void theValueBABY_SHOWERShouldReturnExistingEnum() {
    Category category = Category.valueOf("BABY_SHOWER");
    assertEquals(Category.BABY_SHOWER, category);
  }

  @Test
  void theValueGRADUATIONShouldReturnExistingEnum() {
    Category category = Category.valueOf("GRADUATION");
    assertEquals(Category.GRADUATION, category);
  }

  @Test
  void theValueSPECIAL_DAYSShouldReturnExistingEnum() {
    Category category = Category.valueOf("SPECIAL_DAYS");
    assertEquals(Category.SPECIAL_DAYS, category);
  }

  @Test
  void theValuesMethodShouldReturnAllEnums() {
    Category[] values = Category.values();
    assertEquals(5, values.length);
  }
}
