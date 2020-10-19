package io.rcardin.hexagonal.portfolio.pricealert

import io.rcardin.hexagonal.portfolio.Portfolio
import kotlinx.coroutines.flow.collect

class PortfolioAlertForPriceService(
        private val loadByStockNamePort: PortfolioLoadByStockNamePort
): PortfolioAlertForPriceUseCase {
    override suspend fun alertPriceFalling(stock: String) {
        loadByStockNamePort.loadPortfoliosHavingStock(stock)
                .collect { portfolio: Portfolio ->

                }
    }
}