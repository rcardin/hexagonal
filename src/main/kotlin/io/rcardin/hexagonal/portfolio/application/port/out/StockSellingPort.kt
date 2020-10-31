package io.rcardin.hexagonal.portfolio.application.port.out

interface StockSellingPort {
    suspend fun removeStockFromPortfolio(portfolio: String, stock: String, quantity: Long): Boolean
}
