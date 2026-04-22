package org.mollen.service;

import net.devh.boot.grpc.server.service.GrpcService;
import ru.tinkoff.piapi.contract.v1.Currency;
import ru.tinkoff.piapi.contract.v1.Instrument;
import ru.tinkoff.piapi.contract.v1.InstrumentIdType;

@GrpcService
public class GrpcInstrumentDataFetcher {

    public Currency getCurrencyById(InstrumentIdType idType, String classCode, String id, ) {

    }
}
