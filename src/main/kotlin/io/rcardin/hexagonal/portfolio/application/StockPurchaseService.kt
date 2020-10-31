package io.rcardin.hexagonal.portfolio.application

import io.rcardin.hexagonal.portfolio.application.port.`in`.StockPurchaseUseCase
import io.rcardin.hexagonal.portfolio.application.port.out.StockPurchasePort

class StockPurchaseService(private val stockPurchasePort: StockPurchasePort) : StockPurchaseUseCase {
    override suspend fun buy(command: StockPurchaseUseCase.StockPurchaseCommand): Boolean =
        stockPurchasePort.addStockToPortfolio(
            command.portfolio,
            command.stock,
            command.quantity
        )
}
