package org.mollen.record;

public record Share(
        String figi,
        String ticker,
        String name,
        String currency,
        String sector,
        int lot,
        boolean buyAvailable) {}