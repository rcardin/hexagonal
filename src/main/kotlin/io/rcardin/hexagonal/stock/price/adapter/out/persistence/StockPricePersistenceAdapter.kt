package io.rcardin.hexagonal.stock.price.adapter.out.persistence

import io.rcardin.hexagonal.stock.price.application.port.out.StockPriceUpdatePort
import io.rcardin.hexagonal.stock.price.domain.StockPrice
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class StockPricePersistenceAdapter(private val repository: StockPriceRepository) :
    StockPriceUpdatePort {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override suspend fun updatePrice(stockPrice: StockPrice): Boolean {
        return try {
            repository.save(stockPrice.toMongo())
            true
        } catch (e: Exception) {
            logger.error("Error during the persistence of the stock price {}", stockPrice)
            false
        }
    }
}
