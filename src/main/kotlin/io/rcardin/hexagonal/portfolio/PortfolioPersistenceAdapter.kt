package io.rcardin.hexagonal.portfolio

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class PortfolioPersistenceAdapter(
        private val repository: PortfolioRepository): PortfolioCreationPort {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override suspend fun createPortfolio(portfolio: Portfolio): Boolean {
        return try {
            repository.save(portfolio)
            true
        } catch (e: Exception) {
            logger.error("Error during the persistence of the portfolio {}", portfolio.name)
            false
        }
    }
}