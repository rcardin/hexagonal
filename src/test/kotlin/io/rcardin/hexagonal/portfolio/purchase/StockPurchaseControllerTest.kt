package io.rcardin.hexagonal.portfolio.purchase

import io.rcardin.hexagonal.portfolio.creation.PortfolioCreationController
import io.rcardin.hexagonal.portfolio.creation.PortfolioCreationUseCase
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest(controllers = [ StockPurchaseController::class ])
internal class StockPurchaseControllerTest {

    @MockBean
    lateinit var userCase: StockPurchaseUseCase

    @Autowired
    lateinit var client: WebTestClient

    @Test
    fun buyStock() {
        // TODO
    }
}