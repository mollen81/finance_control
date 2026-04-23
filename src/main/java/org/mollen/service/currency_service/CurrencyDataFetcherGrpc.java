package org.mollen.service.currency_service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.project.grpc.Currency;
import org.project.grpc.GetCurrencyByIdRequest;
import org.project.grpc.GetCurrencyByIdResponse;
import org.project.grpc.InstrumentDataFetcherGrpc;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.piapi.contract.v1.InstrumentIdType;


@GrpcService
public class CurrencyDataFetcherGrpc extends InstrumentDataFetcherGrpc.InstrumentDataFetcherImplBase {
    @Autowired
    private final CurrencyDataFetcher currencyDataFetcher;
    @Autowired
    private final CurrencyUtilService currencyUtilService;

    public CurrencyDataFetcherGrpc(CurrencyDataFetcher currencyDataFetcher, CurrencyUtilService currencyUtilService) {
        this.currencyDataFetcher = currencyDataFetcher;
        this.currencyUtilService = currencyUtilService;
    }


    @Override
    public void getCurrencyById(GetCurrencyByIdRequest request,
                                StreamObserver<GetCurrencyByIdResponse> responseObserver) {
        try {
            InstrumentIdType idType = currencyUtilService.mapToTinkoffInstrumentIdType(
                    request.getIdType());
            Currency currency = currencyDataFetcher.getGrpcCurrencyBy(
                    idType, request.getClassCode(), request.getId());

            responseObserver.onNext(GetCurrencyByIdResponse.newBuilder()
                            .mergeCurrency(currency)
                            .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Currency get by id failed: " + e.getMessage())
                    .withCause(e)
                    .asRuntimeException()
            );
        }
    }

}
