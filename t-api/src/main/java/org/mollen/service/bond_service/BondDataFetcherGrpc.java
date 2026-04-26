package org.mollen.service.bond_service;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.mollen.service.instrument_service.InstrumentUtilService;
import org.project.grpc.Bond;
import org.project.grpc.GetAllBondsResponse;
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
    public void getBondById(GetBondByIdRequest request, StreamObserver<GetBondByIdResponse> responseObserver) {
        try {
            Bond bond = bondDataFetcher.getBondBy(
                    instrumentUtilService.mapToTinkoffInstrumentIdType(request.getIdType()),
                    request.getClassCode(),
                    request.getId());

            responseObserver.onNext(GetBondByIdResponse.newBuilder()
                    .mergeBond(bond)
                    .build());
            responseObserver.onCompleted();
        }
        catch (RuntimeException e) {
            responseObserver.onError(Status.INTERNAL
                    .withCause(e.getCause())
                    .withDescription("Bond service getBondById failed: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void getAllBonds(Empty request, StreamObserver<GetAllBondsResponse> responseObserver) {
        try {
            GetAllBondsResponse response = bondDataFetcher.getAllBonds();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (RuntimeException e) {
            responseObserver.onError(Status.INTERNAL
                    .withCause(e.getCause())
                    .withDescription("Bond service getAllBonds failed: " + e.getMessage())
                    .asRuntimeException());
        }
    }
}
