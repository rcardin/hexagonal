package io.rcardin.hexagonal.portfolio.application.port.out

import io.rcardin.hexagonal.portfolio.domain.Portfolio
import kotlinx.coroutines.flow.Flow

interface PortfolioLoadByStockNamePort {
    suspend fun loadPortfoliosHavingStock(name: String): Flow<Portfolio>
}
