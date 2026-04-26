package org.mollen.service.instrument_service;

import com.google.protobuf.Timestamp;
import org.project.grpc.IdType;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.InstrumentIdType;
import ru.tinkoff.piapi.contract.v1.RiskLevel;

import java.time.Instant;

@Component
public class InstrumentUtilService {

    // map from ru.tinkoff.piapi.contract.v1.InstrumentIdType -> org.project.grpc.IdType and reversed
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

    public IdType mapToInternalInstrumentIdType(
            ru.tinkoff.piapi.contract.v1.InstrumentIdType instrumentIdType) {
        return switch (instrumentIdType) {
            case instrumentIdType.INSTRUMENT_ID_UNSPECIFIED ->
                    IdType.ID_TYPE_UNSPECIFIED;

            case instrumentIdType.INSTRUMENT_ID_TYPE_FIGI ->
                    IdType.ID_TYPE_FIGI;

            case instrumentIdType.INSTRUMENT_ID_TYPE_TICKER ->
                    IdType.ID_TYPE_TICKER;

            case instrumentIdType.INSTRUMENT_ID_TYPE_UID ->
                    IdType.ID_TYPE_UID;

            case instrumentIdType.INSTRUMENT_ID_TYPE_POSITION_UID ->
                    IdType.ID_TYPE_POSITION_UID;

            case instrumentIdType.UNRECOGNIZED ->
                    IdType.UNRECOGNIZED;
        };
    }



    // map from ru.tinkoff.piapi.contract.v1.RiskLevel -> org.project.grpc.RiskLevel and reversed
    public ru.tinkoff.piapi.contract.v1.RiskLevel mapToTinkoffRiskLevel(
            org.project.grpc.RiskLevel riskLevel) {
        return switch (riskLevel) {
            case UNRECOGNIZED -> RiskLevel.RISK_LEVEL_UNSPECIFIED;
            case RISK_LEVEL_UNSPECIFIED -> RiskLevel.RISK_LEVEL_UNSPECIFIED;
            case RISK_LEVEL_LOW -> RiskLevel.RISK_LEVEL_LOW;
            case RISK_LEVEL_MODERATE -> RiskLevel.RISK_LEVEL_MODERATE;
            case RISK_LEVEL_HIGH -> RiskLevel.RISK_LEVEL_HIGH;
            case null, default -> null;
        };
    }

    public org.project.grpc.RiskLevel mapToInternalRiskLevel(
            ru.tinkoff.piapi.contract.v1.RiskLevel riskLevel) {
        return switch (riskLevel) {
            case UNRECOGNIZED -> org.project.grpc.RiskLevel.UNRECOGNIZED;
            case RISK_LEVEL_UNSPECIFIED -> org.project.grpc.RiskLevel.RISK_LEVEL_UNSPECIFIED;
            case RISK_LEVEL_LOW -> org.project.grpc.RiskLevel.RISK_LEVEL_LOW;
            case RISK_LEVEL_MODERATE -> org.project.grpc.RiskLevel.RISK_LEVEL_MODERATE;
            case RISK_LEVEL_HIGH -> org.project.grpc.RiskLevel.RISK_LEVEL_HIGH;
            case null, default -> null;
        };
    }


    // parse String -> Timestamp
    public Timestamp parseTimestampFromString(String time) {
        Instant instant = Instant.parse(time);
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }
}
