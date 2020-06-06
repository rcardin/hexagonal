package io.rcardin.hexagonal.portfolio.creation

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PortfolioCreationConfiguration {
    @Bean
    fun portfolioCreationUseCase(port: PortfolioCreationPort): PortfolioCreationUseCase =
            PortfolioCreationService(port)
}