package com.example.practice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ZipCodeTest {
    @ParameterizedTest
    @CsvSource({
        "123-4567, 1234567", 
        "1234567, 1234567"
    })
    void constructor_ValidZipCode_ShouldStoreNormalizedValue(String input, String expected) {
        ZipCode zip = new ZipCode(input);
        assertEquals(expected, zip.getRaw());
    }

    @ParameterizedTest
    @ValueSource(strings = { "abc-defg", "1234-567", "123456", "", "12-34567" })
    void constructor_InvalidZipCode_ShouldThrowException(String input) {
        assertThrows(IllegalArgumentException.class, () -> new ZipCode(input));
    }

    @Test
    void getFormatted_ShouldReturnFormattedZipCode() {
        ZipCode zip = new ZipCode("1234567");
        assertEquals("123-4567", zip.getFormatted());
    }
}
