from fastapi import APIRouter, Depends, HTTPException, Response
from sqlmodel import Session, select

from .. import models
from ..database import get_session

router = APIRouter()


@router.post('/')
async def add_user(
    user_base: models.UserBase,
    db: Session = Depends(get_session)
) -> models.ExchangeUser:
    user = models.ExchangeUser.from_orm(user_base)

    try:
        db.add(user)
        db.commit()
        db.refresh(user)
    except:
        raise HTTPException(status_code=500, detail='Failed to get create a user')

    return user


@router.patch('/{user_id}')
async def deposit_money(
    user_id: int,
    deposit: models.UserBase,
    db: Session = Depends(get_session)
) -> models.ExchangeUser:
    if deposit.cash < 0:
        raise HTTPException(status_code=400, detail='Can\'t withdraw money')

    query = select(models.ExchangeUser).where(models.ExchangeUser.id == user_id)
    try:
        user = db.exec(query).one()
    except:
        raise HTTPException(status_code=404, detail='Failed to find the user to deposit money')

    user.cash += deposit.cash

    try:
        db.add(user)
        db.commit()
        db.refresh(user)
    except:
        raise HTTPException(status_code=500, detail='Failed to deposit money')

    return user


@router.get('/{user_id}', response_model=models.UserRead)
async def get_portfolio(
    user_id: int,
    db: Session = Depends(get_session)
) -> models.ExchangeUser:
    query = select(models.ExchangeUser).where(models.ExchangeUser.id == user_id)
    try:
        return db.exec(query).one()
    except:
        raise HTTPException(status_code=404, detail='Failed to get info on that user')
