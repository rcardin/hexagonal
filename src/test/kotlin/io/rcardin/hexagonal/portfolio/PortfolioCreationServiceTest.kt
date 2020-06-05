package io.rcardin.hexagonal.portfolio

import com.nhaarman.mockitokotlin2.whenever
import io.rcardin.hexagonal.portfolio.PortfolioCreationUseCase.PortfolioCreationCommand
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
    fun createPortfolioShouldReturnTheResultOfPortfolioCreation() =
            runBlocking {
                val portfolio = Portfolio("portfolio")
                whenever(creationPort.createPortfolio(portfolio)).thenReturn(true)
                Assertions.assertTrue(
                    service.createPortfolio(PortfolioCreationCommand("portfolio"))
                )
            }
}