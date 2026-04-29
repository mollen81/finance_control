package org.mollen.service.share_service;

import org.project.grpc.Decimal;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.Quotation;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.contract.v1.ShareType;

@Component
public class ShareUtilService {
    public ShareType mapToTinkoffShareType(org.project.grpc.ShareType shareType) {
        return switch (shareType) {

            case SHARE_TYPE_ADR ->
                ShareType.SHARE_TYPE_ADR;

            case SHARE_TYPE_GDR ->
                ShareType.SHARE_TYPE_GDR;

            case SHARE_TYPE_MLP ->
                ShareType.SHARE_TYPE_MLP;

            case SHARE_TYPE_REIT ->
                ShareType.SHARE_TYPE_MLP;

            case SHARE_TYPE_COMMON ->
                ShareType.SHARE_TYPE_COMMON;

            case SHARE_TYPE_PREFERRED ->
                ShareType.SHARE_TYPE_PREFERRED;

            case SHARE_TYPE_NY_REG_SHRS ->
                ShareType.SHARE_TYPE_NY_REG_SHRS;

            case SHARE_TYPE_CLOSED_END_FUND ->
                ShareType.SHARE_TYPE_CLOSED_END_FUND;

            case SHARE_TYPE_UNSPECIFIED ->
                ShareType.SHARE_TYPE_UNSPECIFIED;

            case UNRECOGNIZED ->
                ShareType.UNRECOGNIZED;

            case null, default -> null;
        };
    }


    public org.project.grpc.ShareType mapShareTypeFromTinkoffShareType(ShareType shareType) {
        return switch (shareType) {
            case SHARE_TYPE_ADR ->
                    org.project.grpc.ShareType.SHARE_TYPE_ADR;

            case SHARE_TYPE_GDR ->
                    org.project.grpc.ShareType.SHARE_TYPE_GDR;

            case SHARE_TYPE_MLP ->
                    org.project.grpc.ShareType.SHARE_TYPE_MLP;

            case SHARE_TYPE_REIT ->
                    org.project.grpc.ShareType.SHARE_TYPE_MLP;

            case SHARE_TYPE_COMMON ->
                    org.project.grpc.ShareType.SHARE_TYPE_COMMON;

            case SHARE_TYPE_PREFERRED ->
                    org.project.grpc.ShareType.SHARE_TYPE_PREFERRED;

            case SHARE_TYPE_NY_REG_SHRS ->
                    org.project.grpc.ShareType.SHARE_TYPE_NY_REG_SHRS;

            case SHARE_TYPE_CLOSED_END_FUND ->
                    org.project.grpc.ShareType.SHARE_TYPE_CLOSED_END_FUND;

            case SHARE_TYPE_UNSPECIFIED ->
                    org.project.grpc.ShareType.SHARE_TYPE_UNSPECIFIED;

            case UNRECOGNIZED ->
                    org.project.grpc.ShareType.UNRECOGNIZED;

            case null, default -> null;
        };
    }


    public ru.tinkoff.piapi.contract.v1.Share mapToTinkoffShare(org.project.grpc.Share share) {
        return Share.newBuilder()
                .setFigi(share.getFigi())
                .setTicker(share.getTicker())
                .setClassCode(share.getClassCode())
                .setIsin(share.getIsin())
                .setName(share.getName())
                .setUid(share.getUid())
                .setPositionUid(share.getPositionUid())
                .setCurrency(share.getCurrency())
                .setExchange(share.getExchange())
                .setCountryOfRisk(share.getCountryOfRisk())
                .setSector(share.getSector())

                .setLot(share.getLot())
                .setBuyAvailableFlag(share.getBuyAvailable())
                .setSellAvailableFlag(share.getSellAvailable())
                .setApiTradeAvailableFlag(share.getApiTradeAvailable())
                .setWeekendFlag(share.getWeekendFlag())
                .setForIisFlag(share.getForIis())
                .setForQualInvestorFlag(share.getForQualInvestor())
                .setShortEnabledFlag(share.getShortEnabled())

                .setShareType(mapToTinkoffShareType(share.getShareType()))
                .setDivYieldFlag(share.getDivYieldFlag())

                .setDlong(Quotation.newBuilder()
                        .setUnits(share.getDlong().getUnits())
                        .setNano(share.getDlong().getNano())
                        .build())
                .setDshort(Quotation.newBuilder()
                        .setUnits(share.getDshort().getUnits())
                        .setNano(share.getDshort().getNano())
                        .build())
                .setDlongMin(Quotation.newBuilder()
                        .setUnits(share.getDlongMin().getUnits())
                        .setNano(share.getDlongMin().getNano())
                        .build())
                .setDshortMin(Quotation.newBuilder()
                        .setUnits(share.getDshortMin().getUnits())
                        .setNano(share.getDshortMin().getNano())
                        .build())
                .build();
    }


    public org.project.grpc.Share mapShareFromTinkoffShare(ru.tinkoff.piapi.contract.v1.Share share) {
        return org.project.grpc.Share.newBuilder()
                .setFigi(share.getFigi())
                .setTicker(share.getTicker())
                .setClassCode(share.getClassCode())
                .setIsin(share.getIsin())
                .setName(share.getName())
                .setUid(share.getUid())
                .setPositionUid(share.getPositionUid())
                .setCurrency(share.getCurrency())
                .setExchange(share.getExchange())
                .setCountryOfRisk(share.getCountryOfRisk())
                .setSector(share.getSector())

                .setLot(share.getLot())
                .setBuyAvailable(share.getBuyAvailableFlag())
                .setSellAvailable(share.getSellAvailableFlag())
                .setApiTradeAvailable(share.getApiTradeAvailableFlag())
                .setWeekendFlag(share.getWeekendFlag())
                .setForIis(share.getForIisFlag())
                .setForQualInvestor(share.getForQualInvestorFlag())
                .setShortEnabled(share.getShortEnabledFlag())

                .setShareType(mapShareTypeFromTinkoffShareType(share.getShareType()))
                .setDivYieldFlag(share.getDivYieldFlag())

                .setDlong(Decimal.newBuilder()
                        .setUnits(share.getDlong().getUnits())
                        .setNano(share.getDlong().getNano())
                        .build())
                .setDshort(Decimal.newBuilder()
                        .setUnits(share.getDshort().getUnits())
                        .setNano(share.getDshort().getNano())
                        .build())
                .setDlongMin(Decimal.newBuilder()
                        .setUnits(share.getDlongMin().getUnits())
                        .setNano(share.getDlongMin().getNano())
                        .build())
                .setDshortMin(Decimal.newBuilder()
                        .setUnits(share.getDshortMin().getUnits())
                        .setNano(share.getDshortMin().getNano())
                        .build())
                .build();
    }
}
