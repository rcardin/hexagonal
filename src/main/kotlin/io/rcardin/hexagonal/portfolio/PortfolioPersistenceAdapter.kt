package io.rcardin.hexagonal.portfolio

import io.rcardin.hexagonal.portfolio.creation.PortfolioCreationPort
import io.rcardin.hexagonal.portfolio.purchase.StockPurchasePort
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class PortfolioPersistenceAdapter(
        private val repository: PortfolioRepository): PortfolioCreationPort, StockPurchasePort {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override suspend fun createPortfolio(portfolio: Portfolio): Boolean {
        return try {
            repository.save(portfolio.toMongo())
            true
        } catch (e: Exception) {
            logger.error("Error during the persistence of the portfolio {}", portfolio.name)
            false
        }
    }

    override suspend fun addStockToPortfolio(
            portfolio: String,
            stock: String,
            quantity: Long): Boolean {
        return repository.addQuantityToStockInAPortfolio(
                portfolio, stock, quantity)?.let { result ->
            return result.modifiedCount > 0
        } ?: false
    }
}