package org.mollen.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mollen.service.HistoricalMarketDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/v1/market-data")
@RequiredArgsConstructor
@Slf4j
public class MarketDataController {
    private final HistoricalMarketDataService marketDataService;

    @PostMapping("/history/sync")
    public ResponseEntity<String> syncHistoricalData(@RequestBody List<String> tickers) {
        log.info("Получен HTTP-запрос на выгрузку истории для тикеров: {}", tickers);

        CompletableFuture.runAsync(() -> {
            try {
                for (String ticker : tickers) {
                    marketDataService.fetchYearlyData(ticker);
                }
            } catch (Exception e) {
                log.error("Критическая ошибка в фоновом потоке выгрузки данных!", e);
            }
        });

        return ResponseEntity.accepted()
                .body("Запущена асинхронная фоновая выгрузка данных для: " + tickers);
    }
}
