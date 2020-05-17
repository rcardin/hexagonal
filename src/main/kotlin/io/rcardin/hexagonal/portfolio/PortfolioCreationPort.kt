package io.rcardin.hexagonal.portfolio

interface PortfolioCreationPort {
    suspend fun createPortfolio(portfolio: Portfolio): Boolean
}