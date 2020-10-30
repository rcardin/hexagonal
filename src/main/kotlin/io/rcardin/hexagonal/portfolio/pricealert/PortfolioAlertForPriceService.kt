package io.rcardin.hexagonal.portfolio.pricealert

import io.rcardin.hexagonal.portfolio.pricealert.PortfolioAlertForPriceUseCase.PortfolioAlertForPriceCommand
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import org.springframework.beans.factory.annotation.Value

class PortfolioAlertForPriceService(
    private val loadByStockNamePort: PortfolioLoadByStockNamePort,
    private val sendAlertForLowPriceStockPort: PortfolioSendAlertForLowPriceStockPort,
    @Value("\${minimum-price-threshold}") private val minimumPriceThreshold: Double
) : PortfolioAlertForPriceUseCase {

    override suspend fun alertPriceFalling(command: PortfolioAlertForPriceCommand) {
        val stockName = command.stock
        val price = command.price
        loadByStockNamePort.loadPortfoliosHavingStock(stockName)
            .map {
                val maybeStock = it.getStock(stockName)
                maybeStock?.to(it)
            }
            .filterNotNull()
            .filter { (stock, _) ->
                stock.owned * price < minimumPriceThreshold
            }
            .collect { (stock, portfolio) ->
                sendAlertForLowPriceStockPort.sendAlert(
                    portfolio.name,
                    stock.name,
                    stock.owned * price
                )
            }
    }
}
