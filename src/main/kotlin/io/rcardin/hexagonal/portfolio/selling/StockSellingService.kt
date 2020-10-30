package io.rcardin.hexagonal.portfolio.selling

class StockSellingService(private val port: StockSellingPort) : StockSellingUseCase {
    override suspend fun sell(command: StockSellingUseCase.StockSellingCommand): Boolean {
        return port.removeStockFromPortfolio(
            command.portfolio,
            command.stock,
            command.quantity
        )
    }
}
