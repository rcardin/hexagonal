package io.rcardin.hexagonal.portfolio.adapter.`in`.event

import io.rcardin.hexagonal.portfolio.application.port.`in`.PortfolioAlertForPriceUseCase
import io.rcardin.hexagonal.portfolio.application.port.`in`.PortfolioAlertForPriceUseCase.PortfolioAlertForPriceCommand
import kotlinx.coroutines.runBlocking
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class PortfolioAlertForPriceListener(private val useCase: PortfolioAlertForPriceUseCase) {
    @KafkaListener(
        id = "portfolioPriceListener",
        topics = [ "prices" ],
        groupId = "portfolioPrices",
        properties = [
            "key.deserializer=org.apache.kafka.common.serialization.StringDeserializer",
            "value.deserializer=org.apache.kafka.common.serialization.DoubleDeserializer"
        ]
    )
    fun listenToPrices(
        @Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY) stock: String,
        @Payload price: Double
    ) {
        runBlocking {
            useCase.alertPriceFalling(PortfolioAlertForPriceCommand(stock, price))
        }
    }
}
