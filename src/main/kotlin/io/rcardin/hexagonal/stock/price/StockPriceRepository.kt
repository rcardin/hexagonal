package io.rcardin.hexagonal.stock.price

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface StockPriceRepository: CoroutineCrudRepository<MongoStockPrice, String>

/**
 * The persisted BSON is something like the following:
 * <pre>
 *     {
 *       "_id:" "AAPL",
 *       "price": 1234.56
 *     }
 * </pre>
 */
@Document(collation = "stock-price")
data class MongoStockPrice(
        @Id val name: String,
        val price: Double
)

fun Price.toMongo(): MongoStockPrice {
    return MongoStockPrice(
            this.name,
            this.value
    )
}