package io.rcardin.hexagonal.portfolio.purchase

class StockPurchaseService(private val stockPurchasePort: StockPurchasePort): StockPurchaseUseCase {
    override suspend fun buy(command: StockPurchaseUseCase.StockPurchaseCommand): Boolean =
        stockPurchasePort.addStockToPortfolio(
                command.portfolio,
                command.stock,
                command.quantity
        )
}