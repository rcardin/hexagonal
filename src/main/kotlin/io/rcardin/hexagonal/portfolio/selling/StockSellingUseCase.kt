package io.rcardin.hexagonal.portfolio.selling

/**
 * Input port to sell some stocks from a portfolio
 */
interface StockSellingUseCase {

    /**
     * Sells the given quantity for a stock. Returns {@code true} if the operation succeeds,
     * {@code false} otherwise.
     */
    suspend fun sell(command: StockSellingCommand): Boolean

    data class StockSellingCommand(val portfolio: String, val stock: String, val quantity: Long)
}