package io.rcardin.hexagonal.portfolio.adapter.out.persistence

import io.rcardin.hexagonal.portfolio.domain.Portfolio
import io.rcardin.hexagonal.portfolio.domain.Stock
import kotlinx.coroutines.flow.Flow
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PortfolioRepository :
    CoroutineCrudRepository<MongoPortfolio, String>,
    CustomPortfolioRepository {

    @Query("{'stocks.?0': { \$exists: true} }")
    suspend fun findAllHavingStock(stock: String): Flow<MongoPortfolio>
}

/**
 * The persisted BSON is something like the following:
 * <pre>
 *     {
 *       "_id:" "rcardin-portfolio",
 *       "stocks": {
 *         "AAPL": 12345,
 *         "GOOGL": 9876
 *       }
 *     }
 * </pre>
 */
@Document(collection = "portfolio")
data class MongoPortfolio(
    @Id val name: String,
    val stocks: Map<String, Long>
) {
    fun toPortfolio(): Portfolio {
        return Portfolio(
            name,
            stocks.map { Stock(it.key, it.value) }.toSet()
        )
    }
}

fun Portfolio.toMongo(): MongoPortfolio {
    return MongoPortfolio(
        this.name,
        this.stocks
            .groupBy { it.name }
            .mapValues { it.value.first().owned }
    )
}
