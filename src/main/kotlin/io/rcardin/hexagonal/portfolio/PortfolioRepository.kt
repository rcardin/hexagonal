package io.rcardin.hexagonal.portfolio

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PortfolioRepository: CoroutineCrudRepository<MongoPortfolio, String>,
        CustomPortfolioRepository

@Document(collection = "portfolio")
data class MongoPortfolio(
        @Id val name: String,
        val stocks: Map<String, Long>) {
    fun toPortfolio(): Portfolio {
        return Portfolio(
                name,
                stocks.map { Stock(it.key, it.value) }.toSet())
    }
}

fun Portfolio.toMongo(): MongoPortfolio {
    return MongoPortfolio(
            this.name,
            this.stocks
                    .groupBy { it.name }
                    .mapValues { it.value.first().owned })
}