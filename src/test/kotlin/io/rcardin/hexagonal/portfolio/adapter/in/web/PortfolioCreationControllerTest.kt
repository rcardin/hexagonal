package io.rcardin.hexagonal.portfolio.adapter.`in`.web

import com.nhaarman.mockitokotlin2.whenever
import io.rcardin.hexagonal.portfolio.adapter.`in`.web.PortfolioCreationController.PortfolioCreationExceptionHandlers
import io.rcardin.hexagonal.portfolio.application.port.`in`.PortfolioCreationUseCase
import io.rcardin.hexagonal.portfolio.application.port.`in`.PortfolioCreationUseCase.PortfolioCreationCommand
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

@WebFluxTest(
    controllers = [
        PortfolioCreationController::class,
        PortfolioCreationExceptionHandlers::class
    ]
)
internal class PortfolioCreationControllerTest {

    @MockBean
    lateinit var userCase: PortfolioCreationUseCase

    @Autowired
    lateinit var client: WebTestClient

    @Test
    internal fun `Create portfolio should return a 201 status within the Location header`(): Unit =
        runBlocking {
            val command = PortfolioCreationCommand("portfolio")
            whenever(userCase.createPortfolio(command)).thenReturn(true)
            client.post()
                .uri("/portfolios")
                .body(BodyInserters.fromValue("portfolio"))
                .exchange()
                .expectStatus()
                .isCreated
                .expectHeader()
                .valueEquals("Location", "/portfolios/portfolio")
            Unit
        }

    @Test
    internal fun `Create a portfolio should return a 500 status in case of exception`() = runBlocking {
        val command = PortfolioCreationCommand("portfolio")
        whenever(userCase.createPortfolio(command)).thenReturn(false)
        client.post()
            .uri("/portfolios")
            .body(BodyInserters.fromValue("portfolio"))
            .exchange()
            .expectStatus()
            .is5xxServerError
        Unit
    }

    @Test
    internal fun `Create a portfolio should return a 400 status if the name is empty`() =
        runBlocking {
            client.post()
                .uri("/portfolios")
                .body(BodyInserters.fromValue(""))
                .exchange()
                .expectStatus()
                .isBadRequest
                .expectBody()
                .json("{\"label\": \"EMPTY_PORTFOLIO_NAME\", \"description\": \"The name of the portfolio cannot be empty\"}")
            Unit
        }
}
