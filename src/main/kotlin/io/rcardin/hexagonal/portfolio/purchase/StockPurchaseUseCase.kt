package io.rcardin.hexagonal.portfolio.purchase

import io.rcardin.hexagonal.portfolio.Portfolio

/**
 * Input port to buy some stocks and add them into a portfolio
 */
interface StockPurchaseUseCase {
    /**
     * Buys the given quantity for a stock and returns the changed portfolio. If no portfolio with
     * the given name exists, it returns {@code null}.
     */
    suspend fun buy(command: StockPurchaseCommand): Portfolio?

    data class StockPurchaseCommand(val portfolio: String, val stock: String, val quantity: Long)
}