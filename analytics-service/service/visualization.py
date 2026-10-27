import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd

class Visualizer:
    def __init__(self, data: pd.DataFrame):
        self._data = data
        # Настройка стиля графиков
        sns.set_theme(style="whitegrid", palette="muted")
        plt.rcParams['figure.figsize'] = (12, 6)

    def plot_price_dynamics(self, ticker: str):
        """Линейный график: динамика цены закрытия во времени."""
        ticker_data = self._data[self._data['ticker'] == ticker]

        plt.figure()
        sns.lineplot(data=ticker_data, x='timestamp', y='close', linewidth=1.5)
        plt.title(f'Динамика цены закрытия (15m) - {ticker}')
        plt.xlabel('Время')
        plt.ylabel('Цена (руб.)')
        plt.tight_layout()
        plt.show()

    def plot_correlation_heatmap(self):
        """Тепловая карта: матрица корреляций признаков."""
        # Выбираем только числовые ML-признаки
        numeric_cols = ['open', 'high', 'low', 'close', 'volume', 'returns_15m', 'rsi_14', 'volatility_14']
        corr_matrix = self._data[numeric_cols].corr()

        plt.figure(figsize=(10, 8))
        sns.heatmap(corr_matrix, annot=True, fmt=".2f", cmap="coolwarm", square=True, cbar_kws={"shrink": .8})
        plt.title('Матрица корреляций финансовых показателей')
        plt.tight_layout()
        plt.show()

    def plot_volume_comparison(self):
        """Столбчатая диаграмма: сравнение средних объемов торгов по компаниям[cite: 9]."""
        avg_volume = self._data.groupby('ticker')['volume'].mean().reset_index()

        plt.figure()
        sns.barplot(data=avg_volume, x='ticker', y='volume', palette="viridis")
        plt.title('Средний объем торгов за 15-минутный интервал по компаниям')
        plt.xlabel('Тикер')
        plt.ylabel('Средний объем (лоты)')
        plt.tight_layout()
        plt.show()

    def plot_volume_distribution(self):
        """Круговая диаграмма: распределение суммарного объема торгов[cite: 9]."""
        total_volume = self._data.groupby('ticker')['volume'].sum()

        plt.figure(figsize=(8, 8))
        plt.pie(total_volume, labels=total_volume.index, autopct='%1.1f%%', startangle=140, colors=sns.color_palette("pastel"))
        plt.title('Доля компаний в суммарном объеме торгов')
        plt.tight_layout()
        plt.show()