import os
import pandas as pd
import numpy as np
import psycopg2
import warnings
from service.feature_pipeline import FeatureExtractor
from service.descriptive_stats import StatisticalAnalyzer

# Отключаем предупреждения pandas для чистоты вывода
warnings.filterwarnings('ignore')

def main():
    # Читаем DSN из переменных окружения Docker или используем локальный localhost для тестов
    DB_DSN = os.getenv("DB_DSN", "postgresql://user:password@localhost:5432/finance_control")

    conn = psycopg2.connect(DB_DSN)
    raw_df = pd.read_sql("SELECT * FROM market_data ORDER BY ticker, timestamp ASC", conn)
    conn.close()

    print(f"Общее количество сырых записей в БД: {len(raw_df)}")
    print("\n--- ЧАСТОТНАЯ ТАБЛИЦА ПО КАТЕГОРИАЛЬНОЙ ПЕРЕМЕННОЙ TICKER ---")
    freq = raw_df['ticker'].value_counts().reset_index()
    freq.columns = ['Тикер (Категория)', 'Абсолютная частота (кол-во свечей)']
    freq['Относительная доля (%)'] = np.round(freq['Абсолютная частота (кол-во свечей)'] / len(raw_df) * 100, 2)
    print(freq.to_markdown(index=False))

    numeric_cols = ['open', 'high', 'low', 'close', 'volume', 'returns_15m', 'volatility_14', 'sma_50', 'rsi_14']

    for ticker in ['SBER', 'GAZP', 'TATN', 'CHMF', 'MGNT', 'YDEX']:
        t_df = raw_df[raw_df['ticker'] == ticker].copy()
        if t_df.empty:
            continue

        # Приводим Decimal из базы к float для корректной работы pandas/numpy
        for col in ['open', 'high', 'low', 'close']:
            t_df[col] = t_df[col].astype(float)

        ml_df = FeatureExtractor.build_features(t_df)
        analyzer = StatisticalAnalyzer(ml_df)
        stats = analyzer.compute_summary_statistics(numeric_cols)

        print(f"\n--- ОПИСАТЕЛЬНАЯ СТАТИСТИКА ДЛЯ {ticker} (N = {len(ml_df)}) ---")
        print(stats.to_markdown(index=False))

if __name__ == "__main__":
    main()