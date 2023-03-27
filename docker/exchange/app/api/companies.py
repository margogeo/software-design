from fastapi import APIRouter, Depends, HTTPException
from sqlmodel import Session, select

from .. import models
from ..database import get_session

router = APIRouter()


@router.get('/')
async def list_companies(
    db: Session = Depends(get_session)
) -> list[models.Company]:
    query = select(models.Company)

    try:
        return db.exec(query).all()
    except:
        raise HTTPException(status_code=500, detail='Failed to list companies')


@router.post('/')
async def add_company(
    company_base: models.CompanyBase,
    db: Session = Depends(get_session)
) -> models.Company:
    company = models.Company.from_orm(company_base)

    try:
        db.add(company)
        db.commit()
        db.refresh(company)
    except:
        raise HTTPException(status_code=500, detail='Failed to create a company')

    return company


@router.get('/{company_id}', response_model=models.CompanyRead)
async def get_company(
    company_id: int,
    db: Session = Depends(get_session)
) -> models.Company:
    query = select(models.Company).where(models.Company.id == company_id)

    try:
        return db.exec(query).one()
    except:
        raise HTTPException(status_code=404, detail='Failed to get info on that company')
