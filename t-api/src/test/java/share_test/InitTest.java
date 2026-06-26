package share_test;


import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.project.grpc.ShareDataFetcherGrpc;

public class InitTest {
    protected static ShareDataFetcherGrpc.ShareDataFetcherBlockingStub blockingStub;
    protected static Channel channel;


    @BeforeAll
    public static void init() {
        channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        blockingStub = ShareDataFetcherGrpc.newBlockingStub(channel);
    }
}
