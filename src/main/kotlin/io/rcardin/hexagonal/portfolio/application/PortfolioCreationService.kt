package io.rcardin.hexagonal.portfolio.application

import io.rcardin.hexagonal.portfolio.application.port.`in`.PortfolioCreationUseCase
import io.rcardin.hexagonal.portfolio.application.port.out.PortfolioCreationPort
import io.rcardin.hexagonal.portfolio.domain.Portfolio

class PortfolioCreationService(private val creationPort: PortfolioCreationPort) : PortfolioCreationUseCase {
    override suspend fun createPortfolio(command: PortfolioCreationUseCase.PortfolioCreationCommand): Boolean {
        val portfolio = Portfolio(command.name)
        return creationPort.createPortfolio(portfolio)
    }
}
