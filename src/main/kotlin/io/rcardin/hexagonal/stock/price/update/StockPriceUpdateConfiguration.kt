package io.rcardin.hexagonal.stock.price.update

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