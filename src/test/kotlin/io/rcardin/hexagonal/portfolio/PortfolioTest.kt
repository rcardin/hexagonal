package io.rcardin.hexagonal.portfolio

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

internal class PortfolioTest {

    @Test
    internal fun `Buy a freshly new stock should add it to the portfolio`() {
        val portfolio = Portfolio("portfolio")
        val (_, owned) = portfolio.buy("AAPL", 1000L)
        expectThat(owned).contains(Stock("AAPL", 1000L))
    }

    @Test
    internal fun `Buy a stock that is already owned should add the proper quantity`() {
        val portfolio = Portfolio("portfolio", setOf(Stock("AAPL", 1000L)))
        val (_, owned) = portfolio.buy("AAPL", 500L)
        expectThat(owned).contains(Stock("AAPL", 1500L))
    }

    @Test
    internal fun `Getting a stock by name should return the owned stock`() {
        val appleStock = Stock("AAPL", 123L)
        val portfolio = Portfolio("porfolio", setOf(appleStock))
        expectThat(portfolio.getStock("AAPL")).isEqualTo(appleStock)
    }

    @Test
    internal fun `Getting a stock by name should return nothing`() {
        val portfolio = Portfolio("porfolio", setOf(Stock("AAPL", 123L)))
        expectThat(portfolio.getStock("GOOGL")).isNull()
    }
}
