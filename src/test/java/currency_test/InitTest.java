package currency_test;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.project.grpc.InstrumentDataFetcherGrpc;

public class InitTest {
    protected static InstrumentDataFetcherGrpc.InstrumentDataFetcherBlockingStub blockingStub;
    protected static Channel channel;

    // Communication channel
    @BeforeAll
    public static void init() {
        channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        blockingStub = InstrumentDataFetcherGrpc.newBlockingStub(channel);
    }


}
