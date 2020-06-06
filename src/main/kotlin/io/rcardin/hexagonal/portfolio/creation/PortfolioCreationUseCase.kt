package io.rcardin.hexagonal.portfolio.creation

/**
 * Input port to create a new empty portfolio.
 */
interface PortfolioCreationUseCase {
    suspend fun createPortfolio(command: PortfolioCreationCommand): Boolean

    data class PortfolioCreationCommand(val name: String)
}