package share_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.project.grpc.*;

public class ShareDataFetcherGrpcTest extends InitTest {
    @Test
    public void getEtfById() {
        GetShareByIdResponse actual = blockingStub.getShareById(GetShareByIdRequest.newBuilder()
                        .setIdType(IdType.ID_TYPE_TICKER)
                        .setClassCode("TQBR")
                        .setId("ROSB")
                .build());

        GetShareByIdResponse expected = GetShareByIdResponse.newBuilder()
                .mergeShare(Share.newBuilder()
                        .setFigi("BBG000PND2J2")
                        .setTicker("ROSB")
                        .setClassCode("TQBR")
                        .setIsin("RU000A0HHK26")
                        .setName("РОСБАНК")
                        .setUid("3fccb7cc-787b-450d-8799-c3c92c1cb76d")
                        .setPositionUid("b3853bd6-819f-4934-a922-4a266a3e6b84")
                        .setCurrency("rub")
                        .setExchange("unknown")
                        .setCountryOfRisk("RU")
                        .setSector("financial")
                        .setLot(10)
                        .setBuyAvailable(false)
                        .setSellAvailable(false)
                        .setApiTradeAvailable(false)
                        .setWeekendFlag(false)
                        .setForIis(true)
                        .setForQualInvestor(false)
                        .setShortEnabled(false)
                        .setShareType(ShareType.SHARE_TYPE_COMMON)
                        .setDivYieldFlag(false)
                        .setDlong(Decimal.newBuilder()
                                .setUnits(0)
                                .setNano(0)
                                .build())
                        .setDshort(Decimal.newBuilder()
                                .setUnits(0)
                                .setNano(0)
                                .build())
                        .setDlongMin(Decimal.newBuilder()
                                .setUnits(0)
                                .setNano(0)
                                .build())
                        .setDshortMin(Decimal.newBuilder()
                                .setUnits(0)
                                .setNano(0)
                                .build())
                        .build())
                .build();

        Assertions.assertEquals(
                expected.getShare().toBuilder().toString(),
                actual.getShare().toBuilder().toString());
    }
}
