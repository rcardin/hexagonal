package io.rcardin.hexagonal.portfolio.selling

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StockSellingConfiguration {
    @Bean
    fun stockSellingUseCase(port: StockSellingPort): StockSellingUseCase =
            StockSellingService(port)
}