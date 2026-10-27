import os

import pandas as pd

from infrastructure.database import PostgresRepository
from service.descriptive_stats import StatisticalAnalyzer
from service.feature_pipeline import FeatureExtractor


def main() -> None:
    repo = PostgresRepository(os.environ["DB_DSN"])
    repo.connect()

    query = "SELECT * FROM market_data WHERE ticker = 'SBER' ORDER BY timestamp ASC"
    try:
        raw_df = pd.read_sql(query, repo._conn)
    finally:
        repo.close()

    if raw_df.empty:
        print("Данные не найдены. Сначала запустите сбор через t-api.")
        return

    ml_df = FeatureExtractor.build_features(raw_df)
    analyzer = StatisticalAnalyzer(ml_df)

    numeric_cols = ["open", "close", "volume", "returns_15m", "rsi_14", "volatility_14"]
    stats_table = analyzer.compute_summary_statistics(numeric_cols)

    print("\n--- Таблица: Первичный описательный анализ (SBER) ---")
    print(stats_table.to_markdown(index=False))


if __name__ == "__main__":
    main()
