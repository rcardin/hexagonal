package io.rcardin.hexagonal.portfolio.adapter.out.event

import io.rcardin.hexagonal.portfolio.application.port.out.PortfolioSendAlertForLowPriceStockPort
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class PortfolioSendAlertKafkaAdapter(
    private val producer: KafkaTemplate<String, Double>
) : PortfolioSendAlertForLowPriceStockPort {
    override suspend fun sendAlert(portfolio: String, stock: String, price: Double) {
        producer.send("price-alerts", "$portfolio-$stock", price)
    }
}
