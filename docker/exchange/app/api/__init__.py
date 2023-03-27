from fastapi import APIRouter

from .companies import router as companies_router
from .stocks import router as stocks_router

router = APIRouter()
router.include_router(companies_router, prefix='/companies')
router.include_router(stocks_router, prefix='/stocks')
