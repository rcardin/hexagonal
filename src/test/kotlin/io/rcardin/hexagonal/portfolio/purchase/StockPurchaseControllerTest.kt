package io.rcardin.hexagonal.portfolio.purchase

import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

@WebFluxTest(controllers = [StockPurchaseController::class])
internal class StockPurchaseControllerTest {

    @MockBean
    lateinit var userCase: StockPurchaseUseCase

    @Autowired
    lateinit var client: WebTestClient

    @Test
    fun `Buy a stock should return a 200 status if everything is ok`() = runBlocking {
        val command = StockPurchaseUseCase.StockPurchaseCommand(
                "portfolio", "AAPL", 1000L)
        whenever(userCase.buy(command)).thenReturn(true)
        client.put()
                .uri("/portfolios/portfolio/stocks/AAPL")
                .body(BodyInserters.fromValue(1000L))
                .exchange()
                .expectStatus()
                .isOk
        Unit
    }

    @Test
    fun `Buy a stock should return a 404 status if the portfolio does not exist`() =
            runBlocking {
                val command = StockPurchaseUseCase.StockPurchaseCommand(
                        "portfolio", "AAPL", 1000L)
                whenever(userCase.buy(command)).thenReturn(false)
                client.put()
                        .uri("/portfolios/portfolio/stocks/AAPL")
                        .body(BodyInserters.fromValue(1000L))
                        .exchange()
                        .expectStatus()
                        .isNotFound
                Unit
            }
}