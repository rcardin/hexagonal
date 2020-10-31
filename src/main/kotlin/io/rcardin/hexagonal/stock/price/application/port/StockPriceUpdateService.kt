package io.rcardin.hexagonal.stock.price.application.port

import io.rcardin.hexagonal.stock.price.application.port.`in`.StockPriceUpdateUseCase
import io.rcardin.hexagonal.stock.price.application.port.out.StockPriceUpdatePort
import io.rcardin.hexagonal.stock.price.domain.StockPrice

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
