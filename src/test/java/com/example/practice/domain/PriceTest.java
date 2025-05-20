package com.example.practice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class PriceTest {
    @Test
    void normalAmountTest1() {
        Price price = new Price(new BigDecimal("123.454"));
        assertEquals(new BigDecimal("123.45"), price.getAmount());
    }

    @Test
    void normalAmountTest2() {
        Price price = new Price(new BigDecimal("123.455"));
        assertEquals(new BigDecimal("123.46"), price.getAmount());
    }

    @Test
    void normalAmountTest3() {
        Price price = new Price(new BigDecimal("123.456"));
        assertEquals(new BigDecimal("123.46"), price.getAmount());
    }

    @Test
    void nullExceptionTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Price(null);
        });
        assertEquals("金額は必須です", exception.getMessage());
    }

    @Test
    void minusExceptionTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Price(new BigDecimal("-1"));
        });
        assertEquals("金額は0以上である必要があります", exception.getMessage());
    }

    @Test
    void amountAdditionTest() {
        Price price1 = new Price(new BigDecimal("100.123"));
        Price price2 = new Price(new BigDecimal("50.456"));
        Price result = price1.add(price2);

        assertEquals(new BigDecimal("150.58"), result.getAmount());
    }
}
