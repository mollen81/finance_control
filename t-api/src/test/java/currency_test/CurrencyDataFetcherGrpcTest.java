package currency_test;

import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.project.grpc.Currency;
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
                        .build());

        Currency expected = Currency.newBuilder()
                .setFigi("BBG0013J7V24")
                .setTicker("AMDRUB_TOM")
                .setClassCode("CETS")
                .setIsin("")
                .setLot(0)
                .setCurrency("rub")
                .setName("Армянский драм")
                .setExchange("FX")
                .setCountryOfRisk("")
                .setIsoCurrencyName("amd")
                .setUid("86d28f8d-379d-422f-b01d-c32ee0b97501")
                .setPositionUid("4515b482-71f0-4193-aa0a-b91efdab7062")
                .setBuyAvailable(false)
                .setSellAvailable(false)
                .setApiTradeAvailable(false)
                .setForIis(false)
                .setWeekendFlag(false)
                .build();

        Assertions.assertEquals(
                response.getCurrency().toBuilder().toString(),
                expected.toBuilder().toString()
        );
    }

    @Test
    public void getCurrencyByIdNegativeTestForInvalidTickerId() {

        Assertions.assertThrows(StatusRuntimeException.class, () -> blockingStub.getCurrencyById(
                GetCurrencyByIdRequest.newBuilder()
                        .setIdType(IdType.ID_TYPE_TICKER)
                        .setClassCode("CETS")
                        .setId("XXX_TOM")
                        .build())
        );
    }

    @Test
    public void getCurrencyByIdNegativeTestForInvalidClassCode() {
        Assertions.assertThrows(StatusRuntimeException.class, () -> blockingStub.getCurrencyById(
                GetCurrencyByIdRequest.newBuilder()
                        .setIdType(IdType.ID_TYPE_TICKER)
                        .setClassCode("XXXX")
                        .setId("AMDRUB_TOM")
                        .build())
        );
    }

    @Test
    public void getCurrencyByIdNegativeTestForIdType() {
        Assertions.assertThrows(StatusRuntimeException.class, () -> blockingStub.getCurrencyById(
                GetCurrencyByIdRequest.newBuilder()
                        .setIdType(IdType.ID_TYPE_UNSPECIFIED)
                        .setClassCode("XXXX")
                        .setId("AMDRUB_TOM")
                        .build())
        );
    }
}
