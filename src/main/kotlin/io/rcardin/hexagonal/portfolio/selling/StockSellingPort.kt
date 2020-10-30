package io.rcardin.hexagonal.portfolio.selling

interface StockSellingPort {
    suspend fun removeStockFromPortfolio(portfolio: String, stock: String, quantity: Long): Boolean
}
