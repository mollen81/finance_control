package org.mollen.service.bond_service;

import org.mollen.service.instrument_service.InstrumentUtilService;
import org.project.grpc.Bond;
import org.project.grpc.Decimal;
import org.project.grpc.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.MoneyValue;
import ru.tinkoff.piapi.contract.v1.Quotation;

@Component
public class BondUtilService {

    @Autowired
    InstrumentUtilService instrumentUtilService;

    // map from org.project.grpc.Bond -> ru.tinkoff.piapi.contract.v1.Bond and reversed
    public org.project.grpc.Bond mapTinkoffBondToProto(ru.tinkoff.piapi.contract.v1.Bond bond) {
        return Bond.newBuilder()
                .setUid(bond.getUid()) // Uid
                .setTicker(bond.getTicker()) // Ticker
                .setName(bond.getName()) // Name
                .setCurrency(bond.getCurrency()) // Currency
                .setLot(bond.getLot()) // Lot
                .setMinPriceIncrement(Decimal.newBuilder()
                        .setUnits(bond.getMinPriceIncrement().getUnits())
                        .setNano(bond.getMinPriceIncrement().getNano())
                        .build()) // Min Price Increment
                .setNominal(Money.newBuilder()
                        .setCurrency(bond.getInitialNominal().getCurrency())
                        .setUnits(bond.getInitialNominal().getUnits())
                        .setNano(bond.getInitialNominal().getNano())
                        .build()) // Nominal
                .setAciValue(Money.newBuilder()
                        .setCurrency(bond.getAciValue().getCurrency())
                        .setUnits(bond.getAciValue().getUnits())
                        .setNano(bond.getAciValue().getNano())
                        .build()) //ACI value
                .setCouponQuantityPerYear(bond.getCouponQuantityPerYear()) // Coupon quantity per year
                .setMaturityDate(bond.getMaturityDate()) // Maturity date (protobuf.Timestamp)
                .setFloatingCouponFlag(bond.getFloatingCouponFlag()) // Floating Coupon flag
                .setAmortizationFlag(bond.getAmortizationFlag()) // Amortization flag
                .setRiskLevel(instrumentUtilService.mapToInternalRiskLevel(bond.getRiskLevel())) // Risk level
                .setSector(bond.getSector()) // Economics sector
                .setCountryOfRisk(bond.getCountryOfRisk()) // Country of Risk
                .setSubordinatedBond(bond.getSubordinatedFlag()) // Subordinated flag
                .setDlong(Decimal.newBuilder()
                        .setUnits(bond.getDlong().getUnits())
                        .setNano(bond.getDlong().getNano())
                        .build()) // DLong
                .setDshort(Decimal.newBuilder()
                        .setUnits(bond.getDshort().getUnits())
                        .setNano(bond.getDshort().getNano())
                        .build()) // DShort
                .setBuyAvailable(bond.getBuyAvailableFlag()) // Buy available flag
                .setSellAvailable(bond.getSellAvailableFlag()) // Sell vailable flag
                .setApiTradeAvailable(bond.getApiTradeAvailableFlag()) // API trade available flag
                .setForQualInvestor(bond.getForQualInvestorFlag()) // For qualy investor flag
                .build();
    }


    public ru.tinkoff.piapi.contract.v1.Bond mapToTinkoffBond(org.project.grpc.Bond bond) {
        return ru.tinkoff.piapi.contract.v1.Bond.newBuilder()
                .setUid(bond.getUid()) // Uid
                .setTicker(bond.getTicker()) // Ticker
                .setName(bond.getName()) // Name
                .setCurrency(bond.getCurrency()) // Currency
                .setLot(bond.getLot()) // Lot
                .setMinPriceIncrement(Quotation.newBuilder()
                        .setUnits(bond.getMinPriceIncrement().getUnits())
                        .setNano(bond.getMinPriceIncrement().getNano())
                        .build()) // Min Price Increment
                .setNominal(MoneyValue.newBuilder()
                        .setCurrency(bond.getNominal().getCurrency())
                        .setUnits(bond.getNominal().getUnits())
                        .setNano(bond.getNominal().getNano())
                        .build()) // Nominal
                .setAciValue(MoneyValue.newBuilder()
                        .setCurrency(bond.getAciValue().getCurrency())
                        .setUnits(bond.getAciValue().getUnits())
                        .setNano(bond.getAciValue().getNano())
                        .build()) // ACI value
                .setCouponQuantityPerYear(bond.getCouponQuantityPerYear()) // Coupon quantity per year
                .setMaturityDate(bond.getMaturityDate()) // Maturity date (protobuf.Timestamp)
                .setFloatingCouponFlag(bond.getFloatingCouponFlag()) // Floating Coupon flag
                .setAmortizationFlag(bond.getAmortizationFlag()) // Amortization flag
                .setRiskLevel(instrumentUtilService.mapToTinkoffRiskLevel(bond.getRiskLevel())) // Risk level
                .setSector(bond.getSector()) // Economics sector
                .setCountryOfRisk(bond.getCountryOfRisk()) // Country of Risk
                .setSubordinatedFlag(bond.getSubordinatedBond()) // Subordinated flag
                .setDlong(Quotation.newBuilder()
                        .setUnits(bond.getDlong().getUnits())
                        .setNano(bond.getDlong().getNano())
                        .build()) // DLong
                .setDshort(Quotation.newBuilder()
                        .setUnits(bond.getDshort().getUnits())
                        .setNano(bond.getDshort().getNano())
                        .build()) // DShort
                .setBuyAvailableFlag(bond.getBuyAvailable()) // Buy available flag
                .setSellAvailableFlag(bond.getSellAvailable()) // Sell vailable flag
                .setApiTradeAvailableFlag(bond.getApiTradeAvailable()) // API trade available flag
                .setForQualInvestorFlag(bond.getForQualInvestor()) // For qualy investor flag
                .build();
    }

}
