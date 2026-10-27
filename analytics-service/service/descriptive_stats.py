import pandas as pd
import numpy as np

class StatisticalAnalyzer:
    def __init__(self, data: pd.DataFrame):
        self._data = data

    def compute_summary_statistics(self, numeric_columns: list) -> pd.DataFrame:
        """Расчет обязательных статистик для количественных переменных."""
        records = []
        for col in numeric_columns:
            series = self._data[col]

            records.append({
                "Показатель": col,
                "Пропуски": series.isna().sum(),
                "Минимум": np.round(series.min(), 4),
                "Максимум": np.round(series.max(), 4),
                "Среднее": np.round(series.mean(), 4),
                "Медиана": np.round(series.median(), 4),
                "Мода": np.round(series.mode().iloc[0], 4) if not series.mode().empty else None,
                "Дисперсия": np.round(series.var(), 4),
                "Ст. отклонение": np.round(series.std(), 4)
            })

        stats_df = pd.DataFrame(records)
        return stats_df

    def compute_categorical_frequencies(self, categorical_columns: list) -> dict:
        """Частотные таблицы для категориальных переменных (например, ticker)[cite: 8]."""
        frequencies = {}
        for col in categorical_columns:
            frequencies[col] = self._data[col].value_counts().to_frame().reset_index()
        return frequencies