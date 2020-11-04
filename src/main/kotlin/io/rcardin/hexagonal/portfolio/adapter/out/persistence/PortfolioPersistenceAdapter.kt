package io.rcardin.hexagonal.portfolio.adapter.out.persistence

import io.rcardin.hexagonal.portfolio.application.port.out.PortfolioCreationPort
import io.rcardin.hexagonal.portfolio.application.port.out.PortfolioLoadByNamePort
import io.rcardin.hexagonal.portfolio.application.port.out.PortfolioLoadByStockNamePort
import io.rcardin.hexagonal.portfolio.application.port.out.StockPurchasePort
import io.rcardin.hexagonal.portfolio.application.port.out.StockSellingPort
import io.rcardin.hexagonal.portfolio.domain.Portfolio
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class PortfolioPersistenceAdapter(
    private val repository: PortfolioRepository
) : PortfolioCreationPort,
    StockPurchasePort,
    StockSellingPort,
    PortfolioLoadByStockNamePort,
    PortfolioLoadByNamePort {

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

    override suspend fun loadByName(name: String): Portfolio? {
        return repository.findById(name)?.toPortfolio()
    }
}
