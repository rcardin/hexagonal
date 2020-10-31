package io.rcardin.hexagonal.portfolio.application.port.out

interface PortfolioSendAlertForLowPriceStockPort {
    suspend fun sendAlert(portfolio: String, stock: String, price: Double)
}
