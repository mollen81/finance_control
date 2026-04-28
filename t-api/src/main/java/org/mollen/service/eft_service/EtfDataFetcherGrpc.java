package org.mollen.service.eft_service;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.mollen.service.instrument_service.InstrumentUtilService;
import org.project.grpc.Etf;
import org.project.grpc.GetAllEtfsResponse;
import org.project.grpc.GetEtfByIdRequest;
import org.project.grpc.GetEtfByIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.piapi.contract.v1.InstrumentIdType;

@GrpcService
public class EtfDataFetcherGrpc extends org.project.grpc.EtfDataFetcherGrpc.EtfDataFetcherImplBase {

    private final EtfDataFetcher etfDataFetcher;
    private final InstrumentUtilService instrumentUtilService;

    @Autowired
    public EtfDataFetcherGrpc(EtfDataFetcher etfDataFetcher, InstrumentUtilService instrumentUtilService) {
        this.etfDataFetcher = etfDataFetcher;
        this.instrumentUtilService = instrumentUtilService;
    }

    @Override
    public void getEtfById(GetEtfByIdRequest request, StreamObserver<GetEtfByIdResponse> responseObserver) {
        try {
            InstrumentIdType idType = instrumentUtilService.mapToTinkoffInstrumentIdType(request.getIdType());

            Etf etf = etfDataFetcher.getEtfBy(idType, request.getClassCode(), request.getId());

            responseObserver.onNext(GetEtfByIdResponse.newBuilder()
                            .mergeEtf(etf)
                            .build());
            responseObserver.onCompleted();
        }
        catch (RuntimeException e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Eft get by id failed: " + e.getMessage())
                    .withCause(e)
                    .asRuntimeException()
            );
        }
    }

    @Override
    public void getAllEtfs(Empty request, StreamObserver<GetAllEtfsResponse> responseObserver) {
        try {
            GetAllEtfsResponse response = etfDataFetcher.getAllEtfs();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (RuntimeException e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Get all Etfs failed: " + e.getMessage())
                    .withCause(e)
                    .asRuntimeException()
            );
        }
    }
}
