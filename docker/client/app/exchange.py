import json
import requests

from .config import EXCHANGE_URL

def request_price(ticker: str) -> float | None:
    try:
        answer = requests.get(url=f'{EXCHANGE_URL}/stocks/{ticker}')
        return answer.json()['price']
    except:
        return None

def request_trade(ticker: str, quantity: int) -> bool:
    try:
        answer = requests.get(
            url=f'{EXCHANGE_URL}/stocks/{ticker}/trade',
            params={'quantity': quantity}
        )
        return answer.ok
    except:
        return False
