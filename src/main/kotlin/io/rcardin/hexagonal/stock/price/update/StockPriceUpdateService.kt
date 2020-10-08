package io.rcardin.hexagonal.stock.price.update

import io.rcardin.hexagonal.stock.price.Price

class StockPriceUpdateService(private val port: StockPriceUpdatePort) : StockPriceUpdateUseCase {
    override suspend fun updatePrice(command: StockPriceUpdateUseCase.StockPriceUpdateCommand): Boolean {
        return port.updatePrice(command.toPrice())
    }

    private fun StockPriceUpdateUseCase.StockPriceUpdateCommand.toPrice(): Price {
        return Price(
                this.stock,
                this.price
        )
    }
}