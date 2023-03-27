from fastapi import APIRouter, Depends, HTTPException, Response
from sqlmodel import Session, select

from .. import exchange, models
from ..database import get_session

router = APIRouter()


@router.get('/quote/{ticker}')
async def get_quote(
    ticker: str
) -> dict[str, float]:
    price = exchange.request_price(ticker)
    if price is None:
        raise HTTPException(status_code=500, detail='Failed to get the quote')
    return {'price': price}


@router.get('/buy/{ticker}')
async def buy_stocks(
    ticker: str,
    quantity: int,
    user_id: int,
    db: Session = Depends(get_session)
) -> Response:
    query = select(models.User).where(models.User.id == user_id)
    try:
        user = db.exec(query).one()
    except:
        raise HTTPException(status_code=404, detail='Failed to find the user')

    price = exchange.request_price(ticker)
    if price is None:
        raise HTTPException(status_code=500, detail='Failed to get quotes for the ticker')
    total_price = price * quantity

    if quantity > 0 and user.cash < total_price:
        raise HTTPException(status_code=400, detail='Not enough cash')
    user.cash -= total_price

    if not exchange.request_trade(ticker, quantity):
        raise HTTPException(status_code=500, detail='Failed to trade the shares')

    found = False
    for asset in user.portfolio:
        if asset.ticker == ticker:
            asset.quantity += quantity
            found = True
            break
    if not found:
        user.portfolio.append(
            models.Asset(
                ticker=ticker,
                quantity=quantity,
                owner_id=user_id
            )
        )

    try:
        db.add(user)
        db.commit()
    except:
        raise HTTPException(status_code=500, detail='Failed to save state')

    return Response()


@router.get('/sell/{ticker}')
async def sell_stocks(
    ticker: str,
    quantity: int,
    user_id: int,
    db: Session = Depends(get_session)
) -> Response:
    query = select(models.User).where(models.User.id == user_id)
    try:
        user = db.exec(query).one()
    except:
        raise HTTPException(status_code=404, detail='Failed to find the user')

    found = False
    for asset in user.portfolio:
        if asset.ticker == ticker:
            if asset.quantity < quantity:
                raise HTTPException(status_code=400, detail='Not enough shares to sell')
            else:
                asset.quantity -= quantity
                found = True
                break
    if not found:
        raise HTTPException(status_code=400, detail='Not enough shares to sell')

    price = exchange.request_price(ticker)
    if price is None:
        raise HTTPException(status_code=500, detail='Failed to get quotes for the ticker')
    user.cash += price * quantity

    try:
        db.add(user)
        db.commit()
    except:
        raise HTTPException(status_code=500, detail='Failed to save state')

    return Response()
