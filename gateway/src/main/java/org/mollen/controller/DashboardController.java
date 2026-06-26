package org.mollen.controller;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.mollen.record.PortfolioSummary;
import org.mollen.record.Share;
import org.project.grpc.GetShareByIdRequest;
import org.project.grpc.GetShareByIdResponse;
import org.project.grpc.IdType;
import org.project.grpc.ShareDataFetcherGrpc;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class DashboardController {

    @GrpcClient("t-api-server")
    private ShareDataFetcherGrpc.ShareDataFetcherBlockingStub shareDataFetcherBlockingStub;

    @QueryMapping
    public PortfolioSummary portfolioSummary() {
        return new PortfolioSummary(1479500.00, 29500.00, 2.03);
    }

    @QueryMapping
    public Share getShareInfo(@Argument String ticker, @Argument String classCode) {
        var request = GetShareByIdRequest.newBuilder()
                .setIdType(IdType.ID_TYPE_TICKER)
                .setId(ticker)
                .setClassCode(classCode)
                .build();

        GetShareByIdResponse response = shareDataFetcherBlockingStub.getShareById(request);
        org.project.grpc.Share getShare = response.getShare();

        return new Share(
                org.project.grpc.Share.getDefaultInstance().getFigi(),
                org.project.grpc.Share.getDefaultInstance().getTicker(),
                org.project.grpc.Share.getDefaultInstance().getName(),
                org.project.grpc.Share.getDefaultInstance().getCurrency(),
                org.project.grpc.Share.getDefaultInstance().getSector(),
                org.project.grpc.Share.getDefaultInstance().getLot(),
                org.project.grpc.Share.getDefaultInstance().getBuyAvailable()
        );
    }
}



