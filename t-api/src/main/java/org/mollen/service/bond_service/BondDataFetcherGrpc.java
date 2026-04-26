package org.mollen.service.bond_service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.mollen.service.instrument_service.InstrumentUtilService;
import org.project.grpc.Bond;
import org.project.grpc.GetBondByIdRequest;
import org.project.grpc.GetBondByIdResponse;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class BondDataFetcherGrpc extends org.project.grpc.BondDataFetcherGrpc.BondDataFetcherImplBase {

    private final BondDataFetcher bondDataFetcher;
    private final InstrumentUtilService instrumentUtilService;

    @Autowired
    public BondDataFetcherGrpc(BondDataFetcher bondDataFetcher, InstrumentUtilService instrumentUtilService) {
        this.bondDataFetcher = bondDataFetcher;
        this.instrumentUtilService = instrumentUtilService;
    }




    @Override
    public void getBondById(GetBondByIdRequest request, StreamObserver<GetBondByIdResponse> response) {
        try {
            Bond bond = bondDataFetcher.getBondBy(
                    instrumentUtilService.mapToTinkoffInstrumentIdType(request.getIdType()),
                    request.getClassCode(),
                    request.getId());

            response.onNext(GetBondByIdResponse.newBuilder()
                    .mergeBond(bond)
                    .build());
            response.onCompleted();
        } catch (RuntimeException e) {
            response.onError(Status.INTERNAL
                            .withCause(e.getCause())
                            .withDescription(e.getMessage())
                            .asRuntimeException());
        }
    }
}
