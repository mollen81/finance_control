package etf_test;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.project.grpc.EtfDataFetcherGrpc;

import java.util.concurrent.TimeUnit;

public class InitTest {
    protected static EtfDataFetcherGrpc.EtfDataFetcherBlockingStub blockingStub;
    protected static Channel channel;

    // Communication channel
    @BeforeAll
    public static void init() {
        channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        blockingStub = EtfDataFetcherGrpc.newBlockingStub(channel);
    }

    @AfterAll
    public static void shutdown() throws InterruptedException {
        if (channel instanceof io.grpc.ManagedChannel) {
            ((io.grpc.ManagedChannel) channel).shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
