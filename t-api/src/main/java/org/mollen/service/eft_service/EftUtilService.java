package org.mollen.service.eft_service;

import org.project.grpc.Decimal;
import org.project.grpc.Etf;
import org.springframework.stereotype.Component;

@Component
public class EftUtilService {
    public org.project.grpc.Etf mapToProtoEtf(
            ru.tinkoff.piapi.contract.v1.Etf etf) {

        return Etf.newBuilder()
                // Идентификация
                .setFigi(etf.getFigi())
                .setTicker(etf.getTicker())
                .setClassCode(etf.getClassCode())
                .setName(etf.getName())
                .setUid(etf.getUid())
                .setPositionUid(etf.getPositionUid())
                .setCurrency(etf.getCurrency())
                .setExchange(etf.getExchange())
                .setCountryOfRisk(etf.getCountryOfRisk())
                .setSector(etf.getSector())

                // Цена и торговля
                .setLot(etf.getLot())
                .setBuyAvailable(etf.getBuyAvailableFlag())
                .setSellAvailable(etf.getSellAvailableFlag())
                .setApiTradeAvailable(etf.getApiTradeAvailableFlag())
                .setWeekendFlag(etf.getWeekendFlag())
                .setForIis(etf.getForIisFlag())
                .setForQualInvestor(etf.getForQualInvestorFlag())

                // Специфика ETF
                .setFixedCommission(Decimal.newBuilder()
                        .setUnits(etf.getFixedCommission().getUnits())
                        .setNano(etf.getFixedCommission().getNano())
                                .build())
                .setFocusType(etf.getFocusType())
                .setRebalancingFreq(etf.getRebalancingFreq())
                .setNumShares(Decimal.newBuilder()
                        .setUnits(etf.getNumShares().getUnits())
                        .setUnits(etf.getNumShares().getUnits())
                        .build())

                .build();
    }

}
