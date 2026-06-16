package currency_test;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.project.grpc.CurrencyDataFetcherGrpc;

import java.util.concurrent.TimeUnit;

public class InitTest {
    protected static CurrencyDataFetcherGrpc.CurrencyDataFetcherBlockingStub blockingStub;
    protected static Channel channel;

    // Communication channel
    @BeforeAll
    public static void init() {
        channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        blockingStub = CurrencyDataFetcherGrpc.newBlockingStub(channel);
    }

    @AfterAll
    public static void shutdown() throws InterruptedException {
        if (channel instanceof io.grpc.ManagedChannel) {
            ((io.grpc.ManagedChannel) channel).shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
