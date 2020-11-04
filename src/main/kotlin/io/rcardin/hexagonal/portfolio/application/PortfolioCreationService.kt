package io.rcardin.hexagonal.portfolio.application

import io.rcardin.hexagonal.portfolio.application.port.`in`.PortfolioCreationUseCase
import io.rcardin.hexagonal.portfolio.application.port.out.PortfolioCreationPort
import io.rcardin.hexagonal.portfolio.application.port.out.PortfolioLoadByNamePort
import io.rcardin.hexagonal.portfolio.domain.Portfolio

class PortfolioCreationService(
    private val creationPort: PortfolioCreationPort,
    private val loadByNamePort: PortfolioLoadByNamePort
) : PortfolioCreationUseCase {
    override suspend fun createPortfolio(command: PortfolioCreationUseCase.PortfolioCreationCommand): Boolean {
        validateExistence(command)
        val portfolio = Portfolio(command.name)
        return creationPort.createPortfolio(portfolio)
    }

    private suspend fun validateExistence(command: PortfolioCreationUseCase.PortfolioCreationCommand) {
        loadByNamePort.loadByName(command.name)?.let {
            throw IllegalArgumentException("Portfolio ${command.name} already exists")
        }
    }
}
