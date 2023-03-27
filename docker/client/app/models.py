from typing import Optional

from sqlmodel import Field, Relationship, SQLModel


class AssetBase(SQLModel):
    ticker: str = Field()
    quantity: int = Field()


class Asset(AssetBase, table=True):
    id: int = Field(default=None, primary_key=True)
    owner_id: int = Field(foreign_key='user.id')
    owner: 'User' = Relationship(back_populates='portfolio')


class UserBase(SQLModel):
    cash: float = Field()


class User(UserBase, table=True):
    id: Optional[int] = Field(default=None, primary_key=True)
    portfolio: list[Asset] = Relationship(back_populates='owner')


class UserRead(UserBase):
    portfolio: list[AssetBase] = []
