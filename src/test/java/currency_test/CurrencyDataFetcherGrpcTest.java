package currency_test;

import org.junit.jupiter.api.Test;
import org.project.grpc.GetCurrencyByIdRequest;
import org.project.grpc.GetCurrencyByIdResponse;
import org.project.grpc.IdType;

public class CurrencyDataFetcherGrpcTest extends InitTest {
    @Test
    public void getCurrencyByIdTest() {
        GetCurrencyByIdResponse response = blockingStub.getCurrencyById(
                GetCurrencyByIdRequest.newBuilder()
                        .setIdType(IdType.ID_TYPE_TICKER)
                        .setClassCode("CETS")
                        .setId("AMDRUB_TOM")
                        .build()
        );
    }
}
