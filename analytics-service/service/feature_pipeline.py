import pandas as pd
from ta.momentum import RSIIndicator
from ta.trend import SMAIndicator

class FeatureExtractor:
    @staticmethod
    def build_features(df: pd.DataFrame) -> pd.DataFrame:
        """
        Обогащение сырых котировок ML-признаками.
        Данные должны быть предварительно отсортированы по времени.
        """
        # Целевая переменная: процентное изменение цены закрытия
        df['returns_15m'] = df['close'].pct_change() * 100

        # Волатильность (стандартное отклонение доходности за 14 периодов)
        df['volatility_14'] = df['returns_15m'].rolling(window=14).std()

        # Простая скользящая средняя (SMA 50)
        sma = SMAIndicator(close=df['close'], window=50)
        df['sma_50'] = sma.sma_indicator()

        # Индекс относительной силы (RSI 14)
        rsi = RSIIndicator(close=df['close'], window=14)
        df['rsi_14'] = rsi.rsi()

        # Удаляем строки с NaN, образовавшиеся из-за оконных функций (rolling)
        return df.dropna().reset_index(drop=True)