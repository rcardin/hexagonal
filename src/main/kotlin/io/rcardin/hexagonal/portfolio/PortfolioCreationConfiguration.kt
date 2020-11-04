package io.rcardin.hexagonal.portfolio

import io.rcardin.hexagonal.portfolio.application.PortfolioCreationService
import io.rcardin.hexagonal.portfolio.application.port.`in`.PortfolioCreationUseCase
import io.rcardin.hexagonal.portfolio.application.port.out.PortfolioCreationPort
import io.rcardin.hexagonal.portfolio.application.port.out.PortfolioLoadByNamePort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PortfolioCreationConfiguration {
    @Bean
    fun portfolioCreationUseCase(
        creationPort: PortfolioCreationPort,
        loadByNamePort: PortfolioLoadByNamePort
    ): PortfolioCreationUseCase =
        PortfolioCreationService(creationPort, loadByNamePort)
}
