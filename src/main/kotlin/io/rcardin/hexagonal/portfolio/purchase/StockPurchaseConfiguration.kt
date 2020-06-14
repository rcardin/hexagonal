package io.rcardin.hexagonal.portfolio.purchase

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StockPurchaseConfiguration {
    @Bean
    fun stockPurchaseUseCase(port: StockPurchasePort): StockPurchaseUseCase =
            StockPurchaseService(port)
}