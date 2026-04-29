package org.mollen.service.share_service;

import org.mollen.service.instrument_service.InstrumentUtilService;
import org.project.grpc.IdType;
import org.project.grpc.Share;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.Bond;
import ru.tinkoff.piapi.contract.v1.InstrumentIdType;
import ru.tinkoff.piapi.core.InvestApi;

@Component
public class ShareDataFetcher {
    private final ShareUtilService shareUtilService;
    private final InstrumentUtilService instrumentUtilService;
    private final InvestApi investApi;

    @Autowired
    public ShareDataFetcher(ShareUtilService shareUtilService, InstrumentUtilService instrumentUtilService, InvestApi investApi) {
        this.shareUtilService = shareUtilService;
        this.instrumentUtilService = instrumentUtilService;
        this.investApi = investApi;
    }


    public Share getShareBy(
            IdType idType,
            String classCode,
            String id)
    {
        return shareUtilService.mapShareFromTinkoffShare(getShareByIdBy(
                instrumentUtilService.mapToTinkoffInstrumentIdType(idType),
                classCode,
                id)
        );
    }

    public ru.tinkoff.piapi.contract.v1.Share getShareByIdBy(
            InstrumentIdType idType,
            String classCode,
            String id
    )
    {
        return switch (idType) {

            case INSTRUMENT_ID_TYPE_UID ->
                investApi.getInstrumentsService().getShareByUidSync(id);

            case INSTRUMENT_ID_TYPE_POSITION_UID ->
                investApi.getInstrumentsService().getShareByPositionUidSync(id);

            case INSTRUMENT_ID_TYPE_TICKER ->
                investApi.getInstrumentsService().getShareByTickerSync(id, classCode);

            case INSTRUMENT_ID_TYPE_FIGI ->
                investApi.getInstrumentsService().getShareByFigiSync(id);

            case null, default -> null;
        };
    }
}
