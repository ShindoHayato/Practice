package com.example.practice.domain;

public class ZipCode {
    private final String value;

    public ZipCode(String value) {
        if (value == null || !value.matches("^\\d{3}-?\\d{4}$")) {
            throw new IllegalArgumentException("Invalid zip code format. Expected 123-4567 or 1234567.");
        }
        this.value = normalize(value);
    }

    private String normalize(String value) {
        return value.replace("-", "");
    }

    public String getFormatted() {
        return value.substring(0, 3) + "-" + value.substring(3);
    }

    public String getRaw() {
        return value;
    }
}
