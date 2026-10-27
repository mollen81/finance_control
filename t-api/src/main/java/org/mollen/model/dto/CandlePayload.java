package org.mollen.model.dto;

import ru.tinkoff.piapi.contract.v1.HistoricCandle;
import ru.tinkoff.piapi.core.utils.MapperUtils;

import java.math.BigDecimal;
import java.time.Instant;

public record CandlePayload(
        String ticker,
        Instant timestamp,
        BigDecimal open,
        BigDecimal high,
        BigDecimal low,
        BigDecimal close,
        long volume,
        boolean isComplete,
        boolean isHistorical
) {
    // Фабричный метод: берет готовую сущность из SDK и сам переводит Quotation в BigDecimal
    public static CandlePayload fromSdk(String ticker, HistoricCandle candle, boolean isHistorical) {
        return new CandlePayload(
                ticker,
                Instant.ofEpochSecond(candle.getTime().getSeconds(), candle.getTime().getNanos()),
                MapperUtils.quotationToBigDecimal(candle.getOpen()),
                MapperUtils.quotationToBigDecimal(candle.getHigh()),
                MapperUtils.quotationToBigDecimal(candle.getLow()),
                MapperUtils.quotationToBigDecimal(candle.getClose()),
                candle.getVolume(),
                candle.getIsComplete(),
                isHistorical
        );
    }
}