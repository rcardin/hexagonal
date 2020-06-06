package io.rcardin.hexagonal.portfolio.creation

import io.rcardin.hexagonal.portfolio.Portfolio

interface PortfolioCreationPort {
    suspend fun createPortfolio(portfolio: Portfolio): Boolean
}