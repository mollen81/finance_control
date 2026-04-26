package org.mollen.service.bond_service;

import org.project.grpc.Bond;
import org.project.grpc.GetAllBondsResponse;
import org.project.grpc.GetBondByIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.InstrumentIdType;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.List;

@Component
public class BondDataFetcher {

    private final BondUtilService bondUtilService;
    private final InvestApi investApi;

    @Autowired
    public BondDataFetcher(BondUtilService bondUtilService, InvestApi investApi) {
        this.bondUtilService = bondUtilService;
        this.investApi = investApi;
    }


    public Bond getBondBy(InstrumentIdType idType,
                          String classCode,
                          String id) {
        return bondUtilService.mapTinkoffBondToProto(getBondByIdType(idType, classCode, id));
    }


    private ru.tinkoff.piapi.contract.v1.Bond getBondByIdType(InstrumentIdType idType,
                                                             String classCode,
                                                             String id) {
        return switch (idType) {
            case INSTRUMENT_ID_TYPE_FIGI -> investApi.getInstrumentsService().getBondByFigiSync(id);

            case INSTRUMENT_ID_TYPE_TICKER -> investApi.getInstrumentsService().getBondByTickerSync(id, classCode);

            case INSTRUMENT_ID_TYPE_UID -> investApi.getInstrumentsService().getBondByUidSync(id);

            case INSTRUMENT_ID_TYPE_POSITION_UID -> investApi.getInstrumentsService().getBondByPositionUidSync(id);

            case null, default -> null;
        };
    }


    public GetAllBondsResponse getAllBonds() {

        List<Bond> bonds = investApi.getInstrumentsService().getAllBondsSync().stream()
                .map(bondUtilService::mapTinkoffBondToProto)
                .toList();

        List<GetBondByIdResponse> responseList = bonds.stream()
                .map(bond -> GetBondByIdResponse.newBuilder()
                        .mergeBond(bond)
                        .build())
                .toList();

        return GetAllBondsResponse.newBuilder()
                .addAllBonds(responseList)
                .build();
    }
}
