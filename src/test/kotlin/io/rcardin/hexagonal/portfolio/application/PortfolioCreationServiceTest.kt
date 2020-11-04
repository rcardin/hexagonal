package io.rcardin.hexagonal.portfolio.application

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.rcardin.hexagonal.portfolio.application.port.`in`.PortfolioCreationUseCase.PortfolioCreationCommand
import io.rcardin.hexagonal.portfolio.application.port.out.PortfolioCreationPort
import io.rcardin.hexagonal.portfolio.application.port.out.PortfolioLoadByNamePort
import io.rcardin.hexagonal.portfolio.domain.Portfolio
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class PortfolioCreationServiceTest {

    @Test
    internal fun `The creation of a portfolio should return the result of the persistence operation`() =
        runBlocking {
            val portfolio = Portfolio("portfolio")
            val creationPort: PortfolioCreationPort = mock {
                onBlocking { createPortfolio(portfolio) } doReturn true
            }
            val loadPort: PortfolioLoadByNamePort = mock {
                onBlocking { loadByName("portfolio") } doReturn null
            }
            val service = PortfolioCreationService(creationPort, loadPort)
            Assertions.assertTrue(
                service.createPortfolio(PortfolioCreationCommand("portfolio"))
            )
        }
}
