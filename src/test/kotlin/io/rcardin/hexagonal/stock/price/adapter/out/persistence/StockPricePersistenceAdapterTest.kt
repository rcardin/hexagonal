package io.rcardin.hexagonal.stock.price.adapter.out.persistence

import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import io.rcardin.hexagonal.stock.price.domain.StockPrice
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class StockPricePersistenceAdapterTest {

    @Test
    internal fun `The update of a stock price should return true if everything goes fine`() {
        runBlocking {
            val adapter = StockPricePersistenceAdapter(mock())
            val result = adapter.updatePrice(StockPrice("AAPL", 1234.45))
            assertTrue(result)
        }
    }

    @Test
    internal fun `The update of a stock price should return false if something goes wrong`() {
        runBlocking {
            val price = StockPrice("AAPL", 1234.45)
            val repository: StockPriceRepository = mock {
                onBlocking { save(price.toMongo()) } doThrow RuntimeException::class
            }
            val adapter = StockPricePersistenceAdapter(repository)
            val result = adapter.updatePrice(StockPrice("AAPL", 1234.45))
            assertFalse(result)
        }
    }
}
