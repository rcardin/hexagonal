package io.rcardin.hexagonal.portfolio.selling

/**
 * Input port to sell some stocks from a portfolio
 */
interface StockSellingUseCase {

    /**
     * Sells the given quantity for a stock.
     * TODO Take a decision on the return type
     */
    suspend fun sell(command: StockSellingCommand)

    data class StockSellingCommand(val portfolio: String, val stock: String, val quantity: Long)
}