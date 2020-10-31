package io.rcardin.hexagonal.stock.price.application.port.out

import io.rcardin.hexagonal.stock.price.domain.StockPrice

interface StockPriceUpdatePort {
    suspend fun updatePrice(stockPrice: StockPrice): Boolean
}
