package io.rcardin.hexagonal.portfolio

class PortfolioPersistenceAdapter(
        private val repository: PortfolioRepository
): PortfolioCreationPort {

    override suspend fun createPortfolio(portfolio: Portfolio): Boolean {
        return try {
            repository.save(portfolio)
            true
        } catch (e: Exception) {
            // TODO Add some logging
            false
        }
    }
}