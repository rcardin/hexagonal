package io.rcardin.hexagonal.stock.price.update

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StockPriceUpdateConfiguration {
    @Bean
    fun stockPriceUpdateUseCase(port: StockPriceUpdatePort): StockPriceUpdateUseCase =
            StockPriceUpdateService(port)
}