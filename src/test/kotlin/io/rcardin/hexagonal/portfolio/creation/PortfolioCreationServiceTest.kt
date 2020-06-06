package io.rcardin.hexagonal.portfolio.creation

import com.nhaarman.mockitokotlin2.whenever
import io.rcardin.hexagonal.portfolio.Portfolio
import io.rcardin.hexagonal.portfolio.creation.PortfolioCreationUseCase.PortfolioCreationCommand
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class PortfolioCreationServiceTest {

    @Mock
    lateinit var creationPort: PortfolioCreationPort

    @InjectMocks
    lateinit var service: PortfolioCreationService

    @Test
    fun `The creation of a portfolio should return the result of the persistence operation`() =
            runBlocking {
                val portfolio = Portfolio("portfolio")
                whenever(creationPort.createPortfolio(portfolio)).thenReturn(true)
                Assertions.assertTrue(
                    service.createPortfolio(PortfolioCreationCommand("portfolio"))
                )
            }
}