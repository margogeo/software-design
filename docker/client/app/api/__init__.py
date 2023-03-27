from fastapi import APIRouter

from .stocks import router as stocks_router
from .users import router as users_router

router = APIRouter()
router.include_router(stocks_router, prefix='/stocks')
router.include_router(users_router, prefix='/users')
