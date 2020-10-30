package io.rcardin.hexagonal.portfolio.creation

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.rcardin.hexagonal.portfolio.Portfolio
import io.rcardin.hexagonal.portfolio.creation.PortfolioCreationUseCase.PortfolioCreationCommand
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
            val service = PortfolioCreationService(creationPort)
            Assertions.assertTrue(
                service.createPortfolio(PortfolioCreationCommand("portfolio"))
            )
        }
}
