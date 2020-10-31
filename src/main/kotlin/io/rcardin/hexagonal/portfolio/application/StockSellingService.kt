package io.rcardin.hexagonal.portfolio.application

import io.rcardin.hexagonal.portfolio.application.port.`in`.StockSellingUseCase
import io.rcardin.hexagonal.portfolio.application.port.out.StockSellingPort

class StockSellingService(private val port: StockSellingPort) : StockSellingUseCase {
    override suspend fun sell(command: StockSellingUseCase.StockSellingCommand): Boolean {
        return port.removeStockFromPortfolio(
            command.portfolio,
            command.stock,
            command.quantity
        )
    }
}
