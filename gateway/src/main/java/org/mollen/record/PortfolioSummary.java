package org.mollen.record;

public record PortfolioSummary(
        double totalBalanceRub,
        double dailyChangeRub,
        double dailyChangePercent) {}