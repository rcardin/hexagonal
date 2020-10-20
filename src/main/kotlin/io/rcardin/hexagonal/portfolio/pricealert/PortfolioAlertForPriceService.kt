package io.rcardin.hexagonal.portfolio.pricealert

import io.rcardin.hexagonal.portfolio.pricealert.PortfolioAlertForPriceUseCase.PortfolioAlertForPriceCommand
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class PortfolioAlertForPriceService(
        private val loadByStockNamePort: PortfolioLoadByStockNamePort
): PortfolioAlertForPriceUseCase {
    override suspend fun alertPriceFalling(command: PortfolioAlertForPriceCommand) {
        val stock = command.stock;
        loadByStockNamePort.loadPortfoliosHavingStock(stock)
                .map { portfolio ->
                    portfolio.getStock(stock)
                }.collect {
                    TODO()
                }
    }
}