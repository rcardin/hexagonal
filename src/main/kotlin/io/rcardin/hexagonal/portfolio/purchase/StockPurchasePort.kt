package io.rcardin.hexagonal.portfolio.purchase

import io.rcardin.hexagonal.portfolio.Portfolio

interface StockPurchasePort {
    suspend fun addStockToPortfolio(portfolio: String, stock: String, quantity: Long): Portfolio?
}