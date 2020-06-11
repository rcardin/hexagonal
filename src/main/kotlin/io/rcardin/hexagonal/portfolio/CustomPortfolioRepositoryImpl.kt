package io.rcardin.hexagonal.portfolio

import com.mongodb.client.result.UpdateResult
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.isEqualTo

class CustomPortfolioRepositoryImpl(private val mongo: ReactiveMongoTemplate) : CustomPortfolioRepository {
    override suspend fun addQuantityToStockInAPortfolio(portfolio: String, stock: String, quantity: Long): UpdateResult? {
        return mongo.updateFirst(
                Query.query(Criteria.where("_id").isEqualTo(portfolio)),
                Update().inc("stocks.$stock", quantity),
                MongoPortfolio::class.java
        ).block()
    }
}