package io.rcardin.hexagonal.stock.price

import io.rcardin.hexagonal.stock.price.application.port.StockPriceUpdateService
import io.rcardin.hexagonal.stock.price.application.port.`in`.StockPriceUpdateUseCase
import io.rcardin.hexagonal.stock.price.application.port.out.StockPriceUpdatePort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka

@EnableKafka
@Configuration
class StockPriceUpdateConfiguration {
    @Bean
    fun stockPriceUpdateUseCase(port: StockPriceUpdatePort): StockPriceUpdateUseCase =
        StockPriceUpdateService(port)
}
