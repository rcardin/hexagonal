package io.rcardin.hexagonal.portfolio.purchase

import io.rcardin.hexagonal.portfolio.Portfolio

class StockPurchaseService(private val stockPurchasePort: StockPurchasePort): StockPurchaseUseCase {
    override suspend fun buy(command: StockPurchaseUseCase.StockPurchaseCommand): Portfolio? =
        stockPurchasePort.addStockToPortfolio(
                command.portfolio,
                command.stock,
                command.quantity
        )
}