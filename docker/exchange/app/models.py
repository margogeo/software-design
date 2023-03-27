from typing import Optional

from sqlmodel import Field, Relationship, SQLModel


class CompanyBase(SQLModel):
    name: str = Field()


class Company(CompanyBase, table=True):
    id: Optional[int] = Field(default=None, primary_key=True)
    stocks: list['Stock'] = Relationship(back_populates='company')


class StockBase(SQLModel):
    ticker: str = Field(primary_key=True)


class StockUpdate(SQLModel):
    price: float = Field()


class Stock(StockBase, table=True):
    company_id: int = Field(foreign_key='company.id')
    price: float = Field()
    quantity: int = Field()
    company: Company = Relationship(back_populates='stocks')


class CompanyRead(CompanyBase):
    stocks: list[StockBase] = []
