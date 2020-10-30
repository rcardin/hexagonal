package io.rcardin.hexagonal.portfolio.creation

import io.rcardin.hexagonal.portfolio.Portfolio

class PortfolioCreationService(private val creationPort: PortfolioCreationPort) : PortfolioCreationUseCase {
    override suspend fun createPortfolio(command: PortfolioCreationUseCase.PortfolioCreationCommand): Boolean {
        val portfolio = Portfolio(command.name)
        return creationPort.createPortfolio(portfolio)
    }
}
