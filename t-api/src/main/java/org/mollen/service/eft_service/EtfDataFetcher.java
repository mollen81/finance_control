package org.mollen.service.eft_service;

import org.project.grpc.GetAllEtfsResponse;
import org.project.grpc.GetEtfByIdResponse;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.Etf;
import ru.tinkoff.piapi.contract.v1.InstrumentIdType;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.List;

@Component
public class EtfDataFetcher {

    private final EtfUtilService etfUtilService;
    private final InvestApi investApi;


    public EtfDataFetcher(EtfUtilService etfUtilService, InvestApi investApi) {
        this.etfUtilService = etfUtilService;
        this.investApi = investApi;
    }


    // getEtfBy
    public org.project.grpc.Etf getEtfBy(
            InstrumentIdType idType,
            String classCode,
            String id)
    {
        return etfUtilService.mapToProtoEtf(getEtfById(idType, classCode, id));
    }

    private Etf getEtfById(
            InstrumentIdType idType,
            String classCode,
            String id)
    {
        return switch (idType) {
            case INSTRUMENT_ID_TYPE_FIGI ->
                    investApi.getInstrumentsService().getEtfByFigiSync(id);

            case INSTRUMENT_ID_TYPE_TICKER ->
                investApi.getInstrumentsService().getEtfByTickerSync(id, classCode);

            case INSTRUMENT_ID_TYPE_UID ->
                investApi.getInstrumentsService().getEtfByUidSync(id);

            case INSTRUMENT_ID_TYPE_POSITION_UID ->
                investApi.getInstrumentsService().getEtfByPositionUidSync(id);

            case null, default -> null;
        };
    }


    // getAllEtfs
    public GetAllEtfsResponse getAllEtfs() {
        List<org.project.grpc.Etf> etfs = investApi.getInstrumentsService().getAllEtfsSync().stream()
                .map(etfUtilService::mapToProtoEtf)
                .toList();
        List<GetEtfByIdResponse> responses = etfs.stream()
                .map(etf -> GetEtfByIdResponse.newBuilder()
                        .mergeEtf(etf).build())
                .toList();

        return GetAllEtfsResponse.newBuilder().addAllEtfs(responses).build();
    }
}
