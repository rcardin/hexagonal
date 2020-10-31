package io.rcardin.hexagonal.portfolio

import io.rcardin.hexagonal.portfolio.application.StockPurchaseService
import io.rcardin.hexagonal.portfolio.application.port.`in`.StockPurchaseUseCase
import io.rcardin.hexagonal.portfolio.application.port.out.StockPurchasePort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StockPurchaseConfiguration {
    @Bean
    fun stockPurchaseUseCase(port: StockPurchasePort): StockPurchaseUseCase =
        StockPurchaseService(port)
}
