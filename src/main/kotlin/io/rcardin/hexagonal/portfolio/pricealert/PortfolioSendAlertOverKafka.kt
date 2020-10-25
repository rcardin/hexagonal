package io.rcardin.hexagonal.portfolio.pricealert

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class PortfolioSendAlertOverKafka(
        private val producer: KafkaTemplate<String, Double>
): PortfolioSendAlertForLowPriceStockPort {
    override suspend fun sendAlert(portfolio: String, stock: String, price: Double) {
        producer.send("price-alerts", "$portfolio-$stock", price)
    }
}