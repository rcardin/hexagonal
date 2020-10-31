package io.rcardin.hexagonal.portfolio.application.port.`in`

/**
 * Input port to create a new empty portfolio.
 */
interface PortfolioCreationUseCase {
    /**
     * Creates a new Portfolio having the given name.
     */
    suspend fun createPortfolio(command: PortfolioCreationCommand): Boolean

    data class PortfolioCreationCommand(val name: String)
}
