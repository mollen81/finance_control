import psycopg2
from psycopg2.extras import execute_values
from domain.models import CandlePayload
import logging

class PostgresRepository:
    def __init__(self, dsn: str):
        self._dsn = dsn
        self._conn = None

    def connect(self):
        self._conn = psycopg2.connect(self._dsn)
        logging.info("Успешное подключение к PostgreSQL")

    def insert_candles_batch(self, candles: list[CandlePayload]):
        """Пакетная вставка списка свечей в базу данных."""
        query = """
            INSERT INTO market_data
            (ticker, timestamp, open, high, low, close, volume, is_complete, is_historical)
            VALUES %s
        """
        values = [
            (
                c.ticker, c.timestamp, c.open, c.high,
                c.low, c.close, c.volume, c.isComplete, c.isHistorical
            )
            for c in candles
        ]

        with self._conn.cursor() as cur:
            execute_values(cur, query, values)
        self._conn.commit()
        logging.info(f"Успешно сохранено {len(candles)} записей в БД")

    def close(self):
        if self._conn:
            self._conn.close()