import org.junit.jupiter.api.Test;
import org.project.grpc.GetCurrencyByIdRequest;
import org.project.grpc.GetCurrencyByIdResponse;
import org.project.grpc.IdType;
import ru.tinkoff.piapi.contract.v1.Currency;

public class CurrencyDataFetcherGrpcTest extends InitTest {
    @Test
    public void getCurrencyByIdTest() {
        GetCurrencyByIdResponse response = blockingStub.getCurrencyById(
                GetCurrencyByIdRequest.newBuilder()
                        .setIdType(IdType.ID_TYPE_TICKER)
                        .setClassCode("CETS")
                        .setId("GBPRUB")
                        .build()
        );
    }
}
