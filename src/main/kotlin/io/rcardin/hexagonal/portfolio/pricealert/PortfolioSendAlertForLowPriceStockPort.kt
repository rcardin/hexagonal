package io.rcardin.hexagonal.portfolio.pricealert

interface PortfolioSendAlertForLowPriceStockPort {
    suspend fun sendAlert(portfolio: String, stock: String, price: Double)
}
