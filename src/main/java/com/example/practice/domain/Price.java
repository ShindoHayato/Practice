package com.example.practice.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.Getter;

@Getter
public class Price {
    private final BigDecimal amount;

    public Price(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("金額は必須です");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("金額は0以上である必要があります");
        }
        // scale を2に固定する例
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public Price add(Price other) {
        return new Price(this.amount.add(other.amount));
    }
}
