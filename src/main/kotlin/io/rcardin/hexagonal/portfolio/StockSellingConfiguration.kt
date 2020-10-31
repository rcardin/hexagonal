package io.rcardin.hexagonal.portfolio

import io.rcardin.hexagonal.portfolio.application.StockSellingService
import io.rcardin.hexagonal.portfolio.application.port.`in`.StockSellingUseCase
import io.rcardin.hexagonal.portfolio.application.port.out.StockSellingPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StockSellingConfiguration {
    @Bean
    fun stockSellingUseCase(port: StockSellingPort): StockSellingUseCase =
        StockSellingService(port)
}
