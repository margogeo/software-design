from sqlmodel import SQLModel, Session, create_engine

from . import config
from . import models

engine = create_engine(config.DATABASE_URL)


def create_db_and_tables():
    SQLModel.metadata.create_all(engine)


def get_session():
    with Session(engine) as session:
        yield session
