package io.rcardin.hexagonal.portfolio.application.port.out

import io.rcardin.hexagonal.portfolio.domain.Portfolio

interface PortfolioCreationPort {
    suspend fun createPortfolio(portfolio: Portfolio): Boolean
}
