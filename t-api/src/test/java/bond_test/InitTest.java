package bond_test;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.mollen.service.instrument_service.InstrumentUtilService;
import org.project.grpc.BondDataFetcherGrpc;

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

}
