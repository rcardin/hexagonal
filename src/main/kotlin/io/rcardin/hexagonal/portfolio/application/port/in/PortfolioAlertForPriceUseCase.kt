package io.rcardin.hexagonal.portfolio.application.port.`in`

/**
 * Generates a price alert for all the portfolios having the given stock prices
 * under a given threshold
 */
interface PortfolioAlertForPriceUseCase {
    suspend fun alertPriceFalling(command: PortfolioAlertForPriceCommand)

    data class PortfolioAlertForPriceCommand(val stock: String, val price: Double)
}
