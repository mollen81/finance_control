from pydantic import BaseModel
from datetime import datetime
from decimal import Decimal

class CandlePayload(BaseModel):
    ticker: str
    timestamp: datetime
    open: Decimal
    high: Decimal
    low: Decimal
    close: Decimal
    volume: int
    isComplete: bool
    isHistorical: bool