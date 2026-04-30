package org.mollen.service.currency_service;

import org.project.grpc.Currency;
import org.project.grpc.GetAllCurrenciesResponse;
import org.project.grpc.GetBondByIdResponse;
import org.project.grpc.GetCurrencyByIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.InstrumentIdType;
import ru.tinkoff.piapi.contract.v1.InstrumentStatus;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.List;


@Component
public class CurrencyDataFetcher {
    @Autowired
    private final InvestApi investApi;
    @Autowired
    private final CurrencyUtilService currencyUtilService;


    public CurrencyDataFetcher(InvestApi investApi, CurrencyUtilService currencyUtilService) {
        this.investApi = investApi;
        this.currencyUtilService = currencyUtilService;
    }


    // getCurrencyById
    public Currency getGrpcCurrencyBy(InstrumentIdType idType,
                                      String classCode,
                                      String id)
    {
        try {
            return currencyUtilService.mapToProtoCurrency(getCurrencyByIdType(idType, classCode, id));
        }
        catch (NullPointerException e) {
            return null;
        }
    }


    private ru.tinkoff.piapi.contract.v1.Currency getCurrencyByIdType(
            InstrumentIdType idType,
            String classCode,
            String id)
    {
        return switch (idType) {
            case INSTRUMENT_ID_TYPE_FIGI ->
                    investApi.getInstrumentsService().getCurrencyByFigiSync(id);

            case INSTRUMENT_ID_TYPE_TICKER ->
                    investApi.getInstrumentsService().getCurrencyByTickerSync(id, classCode);

            case INSTRUMENT_ID_TYPE_UID ->
                    investApi.getInstrumentsService().getCurrencyByUidSync(id);

            case INSTRUMENT_ID_TYPE_POSITION_UID ->
                investApi.getInstrumentsService().getCurrencyByPositionUidSync(id);

            case INSTRUMENT_ID_UNSPECIFIED -> null;

            case UNRECOGNIZED -> null;

        };
    }



    // getAllCurrencies
    public GetAllCurrenciesResponse getAllGrpcCurrencies() {

        List<Currency> currencies = investApi.getInstrumentsService().getAllCurrenciesSync().stream()
                .map(currencyUtilService::mapToProtoCurrency)
                .toList();

        List<GetCurrencyByIdResponse> responseList = currencies.stream()
                .map(currency -> GetCurrencyByIdResponse.newBuilder()
                        .setCurrency(currency)
                        .build())
                .toList();


        return GetAllCurrenciesResponse.newBuilder()
                .addAllCurrencies(responseList)
                .build();
    }

}
