package io.rcardin.hexagonal.stock.price.update

/**
 * Input port to update the price of a stock.
 */
interface StockPriceUpdateUseCase {
    /**
     * Updates the price of a given stock and returns {@code true} if the operation succeed. If no
     * stock with the given name exists, it inserts the required information. Returns {@code false}
     * if the process goes wrong.
     */
    suspend fun updatePrice(command: StockPriceUpdateCommand): Boolean

    data class StockPriceUpdateCommand(val stock: String, val price: Double)
}
