package bond_test;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mollen.service.instrument_service.InstrumentUtilService;
import org.project.grpc.BondDataFetcherGrpc;

import java.util.concurrent.TimeUnit;

public class InitTest {
    protected static BondDataFetcherGrpc.BondDataFetcherBlockingStub blockingStub;
    protected static Channel channel;
    protected static InstrumentUtilService instrumentUtilService = new InstrumentUtilService();

    // Communication channel
    @BeforeAll
    public static void init() {
         channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                 .usePlaintext()
                .build();

         blockingStub = BondDataFetcherGrpc.newBlockingStub(channel);
    }

    @AfterAll
    public static void shutdown() throws InterruptedException {
        if (channel instanceof io.grpc.ManagedChannel) {
            ((io.grpc.ManagedChannel) channel).shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
