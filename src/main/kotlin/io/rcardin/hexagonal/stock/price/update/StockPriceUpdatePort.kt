package io.rcardin.hexagonal.stock.price.update

interface StockPriceUpdatePort {
    suspend fun updatePrice(stock: String, price: Double): Boolean
}