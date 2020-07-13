package io.rcardin.hexagonal.portfolio.selling

import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

@WebFluxTest(controllers = [StockSellingController::class])
internal class StockSellingControllerTest {

    @MockBean
    lateinit var useCase: StockSellingUseCase

    @Autowired
    lateinit var client: WebTestClient

    @Test
    fun `Sell a stock should return 200 if everything is ok`() = runBlocking {
        val command = StockSellingUseCase.StockSellingCommand(
                "portfolio", "AAPL", 100L)
        whenever(useCase.sell(command)).thenReturn(true)
        client.delete()
                .uri("/portfolios/portfolio/stocks/AAPL?quantity={qty}", mapOf("qty" to 100L))
                .exchange()
                .expectStatus()
                .isOk
        Unit
    }

    @Test
    fun `Sell a stock should return 400 if something goes wrong`() = runBlocking {
        val command = StockSellingUseCase.StockSellingCommand(
                "portfolio", "AAPL", 100L)
        whenever(useCase.sell(command)).thenReturn(false)
        client.delete()
                .uri("/portfolios/portfolio/stocks/AAPL?quantity={qty}", mapOf("qty" to 100L))
                .exchange()
                .expectStatus()
                .isBadRequest
        Unit
    }
}