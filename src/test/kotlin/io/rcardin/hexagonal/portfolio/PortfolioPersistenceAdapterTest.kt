package io.rcardin.hexagonal.portfolio

import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class PortfolioPersistenceAdapterTest {

    @Mock
    lateinit var repository: PortfolioRepository

    @InjectMocks
    lateinit var persistenceAdapter: PortfolioPersistenceAdapter

    @Test
    internal fun `The creation of a portfolio should return true if everything goes fine`() =
            runBlocking {
                val portfolio = Portfolio("portfolio")
                val mongoPortfolio = portfolio.toMongo()
                whenever(repository.save(mongoPortfolio)).thenReturn(mongoPortfolio)
                val result = persistenceAdapter.createPortfolio(portfolio)
                assertTrue(result)
            }

    @Test
    internal fun `The creation of a portfolio should return false if something goes wrong`() =
            runBlocking {
                val portfolio = Portfolio("portfolio")
                whenever(repository.save(portfolio.toMongo())).thenThrow(RuntimeException())
                val result = persistenceAdapter.createPortfolio(portfolio)
                assertFalse(result)
            }
}