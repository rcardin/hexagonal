package io.rcardin.hexagonal.portfolio.application.port.`in`

/**
 * Input port to buy some stocks and add them into a portfolio
 */
interface StockPurchaseUseCase {
    /**
     * Buys the given quantity for a stock and returns {@code true} if the operation succeed. If no
     * portfolio with the given name exists, it returns {@code false}.
     */
    suspend fun buy(command: StockPurchaseCommand): Boolean

    data class StockPurchaseCommand(val portfolio: String, val stock: String, val quantity: Long)
}
