package io.rcardin.hexagonal.portfolio.application.port.out

interface StockPurchasePort {
    suspend fun addStockToPortfolio(portfolio: String, stock: String, quantity: Long): Boolean
}
