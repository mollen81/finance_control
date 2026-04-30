package etf_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.project.grpc.*;

public class EtfDataFetcherGrpcTest extends InitTest {
    @Test
    public void getEtfById() {

        GetEtfByIdResponse actual = blockingStub.getEtfById(GetEtfByIdRequest.newBuilder()
                        .setIdType(IdType.ID_TYPE_TICKER)
                        .setClassCode("SPBRU")
                        .setId("TRUR@")
                        .build());

        GetEtfByIdResponse expected = GetEtfByIdResponse.newBuilder()
                .mergeEtf(Etf.newBuilder()
                        .setFigi("TCS60A1011U5")
                        .setTicker("TRUR@")
                        .setClassCode("SPBRU")
                        .setName("Вечный портфель")
                        .setUid("d16d8124-ce0c-4869-9efb-98700332feab")
                        .setPositionUid("57ab7be6-db70-4651-9169-fa24658452ad")
                        .setCurrency("rub")
                        .setExchange("spb_etf_t")
                        .setCountryOfRisk("RU")
                        .setSector("")
                        .setLot(1)
                        .setBuyAvailable(true)
                        .setSellAvailable(true)
                        .setApiTradeAvailable(true)
                        .setWeekendFlag(false)
                        .setForIis(true)
                        .setForQualInvestor(false)
                        .setFixedCommission(Decimal.newBuilder()
                                .setUnits(2)
                                .setNano(0)
                                .build())
                        .setFocusType("mixed_allocation")
                        .setRebalancingFreq("annual")
                        .setNumShares(Decimal.newBuilder()
                                .setUnits(0)
                                .setNano(0)
                                .build())
                        .build())
                .build();

        Assertions.assertEquals(
                expected.getEtf().toBuilder().toString(),
                actual.getEtf().toBuilder().toString());
    }
}
