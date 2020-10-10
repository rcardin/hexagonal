package io.rcardin.hexagonal.stock.price

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
internal class StockPricePersistenceAdapterTest {

    @Mock
    lateinit var repository: StockPriceRepository

    @InjectMocks
    lateinit var adapter: StockPricePersistenceAdapter

    @Test
    internal fun `The update of a stock price should return true if everything goes fine`() {
        runBlocking {
            val result = adapter.updatePrice(Price("AAPL", 1234.45))
            assertTrue(result)
        }
    }

    @Test
    internal fun `The update of a stock price should return false if something goes wrong`() {
        runBlocking {
            val price = Price("AAPL", 1234.45)
            whenever(repository.save(price.toMongo())).thenThrow(RuntimeException::class.java)
            val result = adapter.updatePrice(Price("AAPL", 1234.45))
            assertFalse(result)
        }
    }
}