package io.rcardin.hexagonal.stock.price

import io.rcardin.hexagonal.stock.price.update.StockPriceUpdatePort
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class StockPricePersistenceAdapter(private val repository: StockPriceRepository) :
        StockPriceUpdatePort {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override suspend fun updatePrice(stockPrice: Price): Boolean {
        return try {
            repository.save(stockPrice.toMongo())
            true
        } catch (e: Exception) {
            logger.error("Error during the persistence of the stock price {}", stockPrice)
            false
        }
    }
}