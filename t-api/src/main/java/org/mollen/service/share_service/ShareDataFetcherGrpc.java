package org.mollen.service.share_service;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.project.grpc.*;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class ShareDataFetcherGrpc extends org.project.grpc.ShareDataFetcherGrpc.ShareDataFetcherImplBase {

    private final ShareDataFetcher shareDataFetcher;

    @Autowired
    public ShareDataFetcherGrpc(ShareDataFetcher shareDataFetcher) {
        this.shareDataFetcher = shareDataFetcher;
    }

    @Override
    public void getShareById(GetShareByIdRequest request, StreamObserver<GetShareByIdResponse> responseObserver) {
        try {
            Share share = shareDataFetcher.getShareBy(
                    request.getIdType(),
                    request.getClassCode(),
                    request.getId());


            responseObserver.onNext(GetShareByIdResponse.newBuilder()
                    .mergeShare(share)
                    .build());
            responseObserver.onCompleted();
        }
        catch (RuntimeException e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Get share by id failed: " + e.getMessage())
                    .withCause(e)
                    .asRuntimeException()
            );
        }
    }

    @Override
    public void getAllShares(Empty request, StreamObserver<GetAllSharesResponse> responseObserver) {
        try {
            GetAllSharesResponse response = shareDataFetcher.getAllSharesResponse();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (RuntimeException e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Get all shares failed: " + e.getMessage())
                    .withCause(e)
                    .asRuntimeException()
            );
        }
    }
}
