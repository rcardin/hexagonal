package io.rcardin.hexagonal.stock.price.update

import kotlinx.coroutines.runBlocking
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class StockPriceListener(private val useCase: StockPriceUpdateUseCase) {
    @KafkaListener(
            id = "priceListener",
            topics = [ "prices" ],
            groupId = "prices",
            properties = [
                "key.deserializer=org.apache.kafka.common.serialization.StringDeserializer",
                "value.deserializer=org.apache.kafka.common.serialization.DoubleDeserializer"
            ]
    )
    suspend fun listenToPrices(
            @Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY) stock: String,
            @Payload price: Double) {
        useCase.updatePrice(StockPriceUpdateUseCase.StockPriceUpdateCommand(stock, price))
    }
}