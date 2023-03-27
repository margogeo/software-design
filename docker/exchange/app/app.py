from fastapi import FastAPI

from . import database
from .api import router

app = FastAPI()
app.include_router(router)


@app.on_event('startup')
async def startup_app():
    database.create_db_and_tables()
