package currency_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mollen.config.TinkoffConfig;
import org.mollen.service.currency_service.CurrencyDataFetcher;
import org.mollen.service.currency_service.CurrencyUtilService;
import org.project.grpc.Currency;
import org.project.grpc.IdType;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.piapi.contract.v1.InstrumentIdType;
import ru.tinkoff.piapi.core.InvestApi;

public class CurrencyDataFetcherTest {

    private CurrencyUtilService currencyUtilService = new CurrencyUtilService();
    CurrencyDataFetcher currencyDataFetcher;


    @Test
    public void getGrpcCurrencyByTest() {
        Currency result = currencyDataFetcher.getGrpcCurrencyBy(
                InstrumentIdType.INSTRUMENT_ID_TYPE_TICKER, "CETS", "AMDRUB_TOM"
        );

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

        Assertions.assertEquals(result.toBuilder().toString(),
                                expected.toBuilder().toString());
    }
}
