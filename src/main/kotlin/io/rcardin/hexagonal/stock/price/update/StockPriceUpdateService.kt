package io.rcardin.hexagonal.stock.price.update

class StockPriceUpdateService(private val port: StockPriceUpdatePort) : StockPriceUpdateUseCase {
    override suspend fun updatePrice(command: StockPriceUpdateUseCase.StockPriceUpdateCommand): Boolean {
        return port.updatePrice(command.stock, command.price)
    }
}