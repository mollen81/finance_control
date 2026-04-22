package org.mollen.service.currency_service;

import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.InstrumentIdType;

@Component
public class CurrencyUtilService {
    public org.project.grpc.Currency mapToProtoCurrency(ru.tinkoff.piapi.contract.v1.Currency currency) {
        return org.project.grpc.Currency.newBuilder()
                .setFigi(currency.getFigi())
                .setTicker(currency.getTicker())
                .setClassCode(currency.getClassCode())
                .setIsin(currency.getIsin())
                .setCurrency(currency.getCurrency())
                .setName(currency.getName())
                .setExchange(currency.getExchange())
                .setCountryOfRisk(currency.getCountryOfRisk())
                .setIsoCurrencyName(currency.getIsoCurrencyName())
                .setUid(currency.getUid())
                .setPositionUid(currency.getPositionUid())
                .build();
    }

    public ru.tinkoff.piapi.contract.v1.Currency mapToTinkoffProtoCurrency(
            org.project.grpc.Currency currency) {
        return ru.tinkoff.piapi.contract.v1.Currency.newBuilder()
                .setFigi(currency.getFigi())
                .setTicker(currency.getTicker())
                .setClassCode(currency.getClassCode())
                .setIsin(currency.getIsin())
                .setCurrency(currency.getCurrency())
                .setName(currency.getName())
                .setExchange(currency.getExchange())
                .setCountryOfRisk(currency.getCountryOfRisk())
                .setIsoCurrencyName(currency.getIsoCurrencyName())
                .setUid(currency.getUid())
                .setPositionUid(currency.getPositionUid())
                .build();
    }

    public ru.tinkoff.piapi.contract.v1.InstrumentIdType mapToTinkoffInstrumentIdType(
            org.project.grpc.IdType idType) {
        return switch(idType) {

            case idType.ID_TYPE_FIGI ->
                InstrumentIdType.INSTRUMENT_ID_TYPE_FIGI;

            case idType.ID_TYPE_TICKER ->
                    InstrumentIdType.INSTRUMENT_ID_TYPE_TICKER;

            case idType.ID_TYPE_UID ->
                    InstrumentIdType.INSTRUMENT_ID_TYPE_UID;

            case idType.ID_TYPE_POSITION_UID ->
                    InstrumentIdType.INSTRUMENT_ID_TYPE_POSITION_UID;

            case idType.ID_TYPE_UNSPECIFIED ->
                    InstrumentIdType.INSTRUMENT_ID_UNSPECIFIED;

            case idType.UNRECOGNIZED ->
                    InstrumentIdType.UNRECOGNIZED;
        };
    }
}
