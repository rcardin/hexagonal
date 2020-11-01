package io.rcardin.hexagonal.portfolio

import io.rcardin.hexagonal.portfolio.application.PortfolioAlertForPriceService
import io.rcardin.hexagonal.portfolio.application.port.`in`.PortfolioAlertForPriceUseCase
import io.rcardin.hexagonal.portfolio.application.port.out.PortfolioLoadByStockNamePort
import io.rcardin.hexagonal.portfolio.application.port.out.PortfolioSendAlertForLowPriceStockPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PortfolioAlertForPriceConfiguration {
    @Bean
    fun portfolioAlertForPriceUseCase(
        loadByStockNamePort: PortfolioLoadByStockNamePort,
        portfolioSendAlertStockPort: PortfolioSendAlertForLowPriceStockPort
    ): PortfolioAlertForPriceUseCase =
        PortfolioAlertForPriceService(loadByStockNamePort, portfolioSendAlertStockPort, 100.0)
}
