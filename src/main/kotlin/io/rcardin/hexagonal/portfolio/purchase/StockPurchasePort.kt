package io.rcardin.hexagonal.portfolio.purchase

interface StockPurchasePort {
    suspend fun addStockToPortfolio(portfolio: String, stock: String, quantity: Long): Boolean
}