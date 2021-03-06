package io.rcardin.hexagonal.portfolio.adapter.out.persistence

import com.mongodb.client.result.UpdateResult

interface CustomPortfolioRepository {
    suspend fun addQuantityToStockInAPortfolio(
        portfolio: String,
        stock: String,
        quantity: Long
    ): UpdateResult?

    suspend fun subtractQuantityToStockInAPortfolio(
        portfolio: String,
        stock: String,
        quantity: Long
    ): UpdateResult?
}
