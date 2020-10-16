package io.rcardin.hexagonal.stock.price.update

import io.rcardin.hexagonal.portfolio.Portfolio

interface PortfolioLoadByStockNamePort {
    suspend fun loadPortfoliosHavingStock(name: String): List<Portfolio>
}