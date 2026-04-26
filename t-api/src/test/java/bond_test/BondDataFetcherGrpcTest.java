package bond_test;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.project.grpc.*;

public class BondDataFetcherGrpcTest extends InitTest {

    @Test
    public void getBondByTest() throws InvalidProtocolBufferException {
        GetBondByIdResponse result = blockingStub.getBondById(
                GetBondByIdRequest.newBuilder()
                        .setIdType(IdType.ID_TYPE_TICKER)
                        .setClassCode("TQOB")
                        .setId("SIBN6P1")
                        .build()
        );

        Bond expected = Bond.newBuilder()
                .setUid("f8067628-b54f-43e3-b6c5-6edfcabcb279")
                .setTicker("SU29009RMFS6")
                .setName("ОФЗ 29009")
                .setCurrency("rub")
                .setLot(1)
                .setMinPriceIncrement(Decimal.newBuilder()
                        .setUnits(0)
                        .setNano(1000000)
                        .build())
                .setNominal(Money.newBuilder()
                        .setCurrency("rub")
                        .setUnits(1000)
                        .setNano(0)
                        .build())
                .setAciValue(Money.newBuilder()
                        .setCurrency("rub")
                        .setUnits(91)
                        .setNano(10000000)
                        .build())
                .setCouponQuantityPerYear(2)
                .setMaturityDate(Timestamp.parseFrom("2032-05-05T00:00:00.000Z".getBytes()))
                .setFloatingCouponFlag(true)
                .setAmortizationFlag(false)
                .setRiskLevel(RiskLevel.RISK_LEVEL_LOW)
                .setSector("government")
                .setCountryOfRisk("RU")
                .setSubordinatedBond(false)
                .setDlong(Decimal.newBuilder()
                        .setUnits(0)
                        .setNano(100000000)
                        .build())
                .setDshort(Decimal.newBuilder()
                        .setUnits(0)
                        .setNano(0)
                        .build())
                .setBuyAvailable(true)
                .setSellAvailable(true)
                .setApiTradeAvailable(true)
                .setForQualInvestor(false)
                .build();

        Assertions.assertEquals(
                result.getBond().toBuilder().toString(),
                expected.toBuilder().toString()
        );
    }
}
