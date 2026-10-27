import json
import logging
from kafka import KafkaConsumer
from domain.models import CandlePayload
from infrastructure.database import PostgresRepository

class MarketDataConsumer:
    def __init__(self, bootstrap_servers: str, topic: str, repository: PostgresRepository):
        self._topic = topic
        self._repository = repository
        self._consumer = KafkaConsumer(
            topic,
            bootstrap_servers=bootstrap_servers,
            group_id='analytics_group',
            auto_offset_reset='earliest',
            value_deserializer=lambda m: json.loads(m.decode('utf-8'))
        )

    def start_consuming(self, batch_size: int = 500):
        """Чтение сообщений из Kafka и сохранение батчами в БД."""
        logging.info(f"Ожидание сообщений в топике {self._topic}...")
        batch = []

        try:
            for message in self._consumer:
                # Валидация JSON через Pydantic
                candle = CandlePayload(**message.value)
                batch.append(candle)

                if len(batch) >= batch_size:
                    self._repository.insert_candles_batch(batch)
                    batch.clear()

        except KeyboardInterrupt:
            logging.info("Остановка консьюмера...")
        finally:
            if batch: # Сохраняем остатки
                self._repository.insert_candles_batch(batch)
            self._consumer.close()