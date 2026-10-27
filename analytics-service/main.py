import logging
import os

from infrastructure.database import PostgresRepository
from infrastructure.kafka_client import MarketDataConsumer

logging.basicConfig(level=logging.INFO, format="%(asctime)s - %(levelname)s - %(message)s")


def main() -> None:
    db_dsn = os.environ["DB_DSN"]
    kafka_brokers = os.getenv("KAFKA_BROKERS", "localhost:9092")
    kafka_topic = "market.data.historical"

    repository = PostgresRepository(db_dsn)
    repository.connect()

    try:
        consumer = MarketDataConsumer(kafka_brokers, kafka_topic, repository)
        consumer.start_consuming(batch_size=1000)
    finally:
        repository.close()


if __name__ == "__main__":
    main()
