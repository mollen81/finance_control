package org.mollen.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mollen.config.KafkaConfig;
import org.mollen.model.dto.CandlePayload;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.CandleInterval;
import ru.tinkoff.piapi.contract.v1.HistoricCandle;
import ru.tinkoff.piapi.contract.v1.InstrumentShort;
import ru.tinkoff.piapi.core.InvestApi;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class HistoricalMarketDataService {
    private final InvestApi investApi;
    private final KafkaTemplate<String, CandlePayload> kafkaTemplate;

    public List<CandlePayload> fetchYearlyData(String ticker) {
        log.info("Начинаем выгрузку исторического датасета для {}", ticker);

        // 1. Получаем FIGI по тикеру (класс-код TQBR актуален для акций Мосбиржи)
        InstrumentShort instrument = investApi.getInstrumentsService()
                .findInstrumentSync(ticker)
                .stream()
                .filter(inst -> inst.getClassCode().equals("TQBR"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Инструмент не найден: " + ticker));

        String figi = instrument.getFigi();

        // 2. Определяем годовой интервал
        Instant to = Instant.now();
        Instant from = to.minus(365, ChronoUnit.DAYS);

        List<CandlePayload> dataset = new ArrayList<>();
        Instant currentStart = from;

        // 3. Чанкование: запрашиваем 15-минутные свечи интервалами по 1 дню
        while (currentStart.isBefore(to)) {
            Instant currentEnd = currentStart.plus(1, ChronoUnit.DAYS);
            if (currentEnd.isAfter(to)) {
                currentEnd = to;
            }

            try {
                List<HistoricCandle> chunk = investApi.getMarketDataService()
                        .getCandlesSync(figi, currentStart, currentEnd, CandleInterval.CANDLE_INTERVAL_15_MIN);

                for (HistoricCandle candle : chunk) {
                    CandlePayload payload = CandlePayload.fromSdk(ticker, candle, true);
                    dataset.add(payload);

                    kafkaTemplate.send(KafkaConfig.MARKET_DATA_HISTORICAL_TOPIC, ticker, payload);
                }

                // Небольшая задержка для предотвращения блокировки по Rate Limit API
                Thread.sleep(150);

            } catch (Exception e) {
                log.error("Ошибка при выгрузке чанка для {}: {} - {}", ticker, currentStart, currentEnd, e);
            }

            currentStart = currentEnd;
        }

        log.info("Выгрузка завершена. Собрано {} свечей для {}", dataset.size(), ticker);
        return dataset;
    }
}
