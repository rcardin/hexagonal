package io.rcardin.hexagonal.portfolio.pricealert

import io.rcardin.hexagonal.portfolio.Portfolio
import kotlinx.coroutines.flow.Flow

interface PortfolioLoadByStockNamePort {
    suspend fun loadPortfoliosHavingStock(name: String): Flow<Portfolio>
}