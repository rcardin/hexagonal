package io.rcardin.hexagonal.portfolio

import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull

@DataMongoTest
internal class CustomPortfolioRepositoryImplIT {

    @Autowired
    lateinit var mongo: ReactiveMongoTemplate

    @Autowired
    lateinit var repository: PortfolioRepository

    @BeforeEach
    internal fun setUp() {
        mongo.dropCollection(MongoPortfolio::class.java).block()
    }

    @Test
    fun `Buy a stock should update the quantity for an existing portfolio`() =
            runBlocking {
                val initialPortfolio = MongoPortfolio("portfolio", mapOf(("AAPL" to 500L)))
                mongo.insert(initialPortfolio).awaitSingle()
                val maybeResult = repository.addQuantityToStockInAPortfolio(
                        "portfolio", "AAPL", 100L)
                expectThat(maybeResult).isNotNull()
                maybeResult?.let { result ->
                    expectThat(result.matchedCount).isEqualTo(1)
                    expectThat(result.modifiedCount).isEqualTo(1)
                }
                val modifiedPortfolio = repository.findById("portfolio")
                expectThat(modifiedPortfolio)
                        .isEqualTo(MongoPortfolio(
                                "portfolio",
                                mapOf(("AAPL" to 600L))))
                Unit
            }

    @Test
    fun `Buy a stock should add the freshly new stock for an existing portfolio`() =
            runBlocking {
                val initialPortfolio = MongoPortfolio("portfolio", mapOf())
                mongo.insert(initialPortfolio).awaitSingle()
                val maybeResult = repository.addQuantityToStockInAPortfolio(
                        "portfolio", "AAPL", 100L)
                expectThat(maybeResult).isNotNull()
                maybeResult?.let { result ->
                    expectThat(result.matchedCount).isEqualTo(1)
                    expectThat(result.modifiedCount).isEqualTo(1)
                }
                val modifiedPortfolio = repository.findById("portfolio")
                expectThat(modifiedPortfolio)
                        .isEqualTo(MongoPortfolio(
                                "portfolio",
                                mapOf(("AAPL" to 100L))))
                Unit
            }

    @Test
    fun `Buy a stock for a portfolio that not exists should not make any change`() =
            runBlocking {
                val maybeResult = repository.addQuantityToStockInAPortfolio(
                        "portfolio", "AAPL", 100L)
                expectThat(maybeResult).isNotNull()
                maybeResult?.let { result ->
                    expectThat(result.matchedCount).isEqualTo(0)
                    expectThat(result.modifiedCount).isEqualTo(0)
                }
                Unit
            }
}