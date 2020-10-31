package io.rcardin.hexagonal.portfolio.adapter.out.persistence

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.awaitLast
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.hasSize

@DataMongoTest
class PortfolioRepositoryIT {

    @Autowired
    lateinit var mongo: ReactiveMongoTemplate

    @Autowired
    lateinit var repository: PortfolioRepository

    @Test
    internal fun `The retrieval of portfolios having a stock should work properly`() {
        runBlocking {
            val p1 = MongoPortfolio("p1", mapOf(("AAPL" to 500L), ("GOOGL" to 1234L)))
            val p2 = MongoPortfolio("p2", mapOf(("AAPL" to 500L), ("TSLA" to 345L)))
            mongo.insertAll(listOf(p1, p2)).awaitLast()
            val retrievedPortfolios = repository.findAllHavingStock("TSLA").toList()
            expectThat(retrievedPortfolios)
                .hasSize(1)
                .contains(p2)
        }
    }
}
