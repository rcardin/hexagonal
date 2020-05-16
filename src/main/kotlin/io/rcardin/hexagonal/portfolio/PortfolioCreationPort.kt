package io.rcardin.hexagonal.portfolio

interface PortfolioCreationPort {
    fun createPortfolio(name: String): Boolean
}