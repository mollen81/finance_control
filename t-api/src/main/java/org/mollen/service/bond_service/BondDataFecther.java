package org.mollen.service.bond_service;

import org.project.grpc.Bond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.InstrumentIdType;
import ru.tinkoff.piapi.core.InvestApi;

@Component
public class BondDataFecther {

    private final BondUtilService utilService;
    private final InvestApi investApi;

    @Autowired
    public BondDataFecther(BondUtilService utilService, InvestApi investApi) {
        this.utilService = utilService;
        this.investApi = investApi;
    }


    public Bond getBondBy(InstrumentIdType idType,
                          String classCode,
                          String id) {
        return utilService.mapTinkoffBondToProto(getBondByIdType(idType, classCode, id));
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
}
