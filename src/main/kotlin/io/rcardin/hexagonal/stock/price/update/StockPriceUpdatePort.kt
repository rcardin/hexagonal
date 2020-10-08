package io.rcardin.hexagonal.stock.price.update

import io.rcardin.hexagonal.stock.price.Price

interface StockPriceUpdatePort {
    suspend fun updatePrice(stockPrice: Price): Boolean
}