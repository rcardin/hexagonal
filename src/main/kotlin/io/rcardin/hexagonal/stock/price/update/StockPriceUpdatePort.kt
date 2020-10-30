package io.rcardin.hexagonal.stock.price.update

import io.rcardin.hexagonal.stock.price.StockPrice

interface StockPriceUpdatePort {
    suspend fun updatePrice(stockPrice: StockPrice): Boolean
}
