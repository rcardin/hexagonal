package io.rcardin.hexagonal.portfolio

import io.rcardin.hexagonal.portfolio.creation.PortfolioCreationPort
import io.rcardin.hexagonal.portfolio.pricealert.PortfolioLoadByStockNamePort
import io.rcardin.hexagonal.portfolio.purchase.StockPurchasePort
import io.rcardin.hexagonal.portfolio.selling.StockSellingPort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class PortfolioPersistenceAdapter(
    private val repository: PortfolioRepository
) :
    PortfolioCreationPort, StockPurchasePort, StockSellingPort, PortfolioLoadByStockNamePort {

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
        quantity: Long
    ): Boolean {
        return repository.addQuantityToStockInAPortfolio(
            portfolio, stock, quantity
        )?.let { result ->
            return result.modifiedCount > 0
        } ?: false
    }

    override suspend fun removeStockFromPortfolio(
        portfolio: String,
        stock: String,
        quantity: Long
    ): Boolean {
        return repository.subtractQuantityToStockInAPortfolio(
            portfolio, stock, quantity
        )?.let { result ->
            return result.modifiedCount > 0
        } ?: false
    }

    override suspend fun loadPortfoliosHavingStock(name: String): Flow<Portfolio> {
        return repository.findAllHavingStock(name)
            .map { mongoPortfolio -> mongoPortfolio.toPortfolio() }
    }
}
