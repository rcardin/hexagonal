package io.rcardin.hexagonal.portfolio.pricealert

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.rcardin.hexagonal.portfolio.Portfolio
import io.rcardin.hexagonal.portfolio.Stock
import io.rcardin.hexagonal.portfolio.pricealert.PortfolioAlertForPriceUseCase.PortfolioAlertForPriceCommand
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class PortfolioAlertForPriceServiceTest {

    private val sendAlertForLowPriceStockPort: PortfolioSendAlertForLowPriceStockPort = mock()

    @Test
    internal fun `The service generates no alert if the stock is not present in any portfolio`() {
        val loadByStockNamePort: PortfolioLoadByStockNamePort = mock {
            onBlocking { loadPortfoliosHavingStock("AAPL") } doReturn emptyFlow()
        }
        val service = PortfolioAlertForPriceService(
            loadByStockNamePort, sendAlertForLowPriceStockPort, 10.0
        )
        runBlocking {
            service.alertPriceFalling(PortfolioAlertForPriceCommand("AAPL", 123.4))
            verify(sendAlertForLowPriceStockPort, never()).sendAlert(any(), any(), any())
        }
    }

    @Test
    internal fun `The service generates no alert if the owned amount does not fall down below the given threshold`() {
        val loadByStockNamePort: PortfolioLoadByStockNamePort = mock {
            onBlocking { loadPortfoliosHavingStock("AAPL") } doReturn
                flowOf(Portfolio("p1", setOf(Stock("AAPL", 10))))
        }
        val service = PortfolioAlertForPriceService(
            loadByStockNamePort, sendAlertForLowPriceStockPort, 100.0
        )
        runBlocking {
            service.alertPriceFalling(PortfolioAlertForPriceCommand("AAPL", 123.4))
            verify(sendAlertForLowPriceStockPort, never()).sendAlert(any(), any(), any())
        }
    }

    @Test
    internal fun `The service generates an alert if the owned amount falls down below the given threshold`() {
        val loadByStockNamePort: PortfolioLoadByStockNamePort = mock {
            onBlocking { loadPortfoliosHavingStock("AAPL") } doReturn
                flowOf(Portfolio("p1", setOf(Stock("AAPL", 10))))
        }
        val service = PortfolioAlertForPriceService(
            loadByStockNamePort, sendAlertForLowPriceStockPort, 2000.0
        )
        runBlocking {
            service.alertPriceFalling(PortfolioAlertForPriceCommand("AAPL", 123.4))
            verify(sendAlertForLowPriceStockPort, times(1))
                .sendAlert("p1", "AAPL", 1234.0)
        }
    }
}
