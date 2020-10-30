package io.rcardin.hexagonal.stock.price.update

import io.rcardin.hexagonal.stock.price.StockPrice

class StockPriceUpdateService(private val port: StockPriceUpdatePort) : StockPriceUpdateUseCase {
    override suspend fun updatePrice(command: StockPriceUpdateUseCase.StockPriceUpdateCommand): Boolean {
        return port.updatePrice(command.toPrice())
    }

    private fun StockPriceUpdateUseCase.StockPriceUpdateCommand.toPrice(): StockPrice {
        return StockPrice(
            this.stock,
            this.price
        )
    }
}
