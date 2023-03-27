from fastapi import APIRouter, Depends, HTTPException, Response
from sqlmodel import Session, select

from .. import models
from ..database import get_session

router = APIRouter()


@router.get('/', response_model=list[models.StockBase])
async def list_stocks(
    db: Session = Depends(get_session)
) -> list[models.Stock]:
    query = select(models.Stock)
    try:
        return db.exec(query).all()
    except:
        raise HTTPException(status_code=500, detail='Failed to list stocks')


@router.post('/')
async def add_stock(
    stock: models.Stock,
    db: Session = Depends(get_session)
) -> models.Stock:
    try:
        db.add(stock)
        db.commit()
        db.refresh(stock)
    except:
        raise HTTPException(status_code=500, detail='Failed to create a stock')

    return stock


@router.patch('/{ticker}')
async def change_price(
    ticker: str,
    update: models.StockUpdate,
    db: Session = Depends(get_session)
) -> models.Stock:
    query = select(models.Stock).where(models.Stock.ticker == ticker)
    try:
        stock = db.exec(query).one()
    except:
        raise HTTPException(status_code=404, detail='Failed to find the stock to update')

    stock.price = update.price

    try:
        db.add(stock)
        db.commit()
        db.refresh(stock)
    except:
        raise HTTPException(status_code=500, detail='Failed to update the price of a stock')

    return stock


@router.get('/{ticker}/trade')
async def trade_stock(
    ticker: str,
    quantity: int,
    db: Session = Depends(get_session)
) -> Response:
    query = select(models.Stock).where(models.Stock.ticker == ticker)
    try:
        stock = db.exec(query).one()
    except:
        raise HTTPException(status_code=404, detail='Failed to find the stock to update')

    if stock.quantity - quantity < 0:
        raise HTTPException(status_code=400, detail='The exchange has not enough shares to trade')
    stock.quantity -= quantity

    try:
        db.add(stock)
        db.commit()
        db.refresh(stock)
    except:
        raise HTTPException(status_code=500, detail='Failed to purchase stocks')

    return Response()


@router.get('/{ticker}')
async def get_stock(
    ticker: str,
    db: Session = Depends(get_session)
) -> models.Stock:
    query = select(models.Stock).where(models.Stock.ticker == ticker)
    try:
        return db.exec(query).one()
    except:
        raise HTTPException(status_code=404, detail='Failed to get info on that stock')
