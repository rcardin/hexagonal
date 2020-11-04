package io.rcardin.hexagonal.portfolio.application.port.out

import io.rcardin.hexagonal.portfolio.domain.Portfolio

interface PortfolioLoadByNamePort {
    suspend fun loadByName(name: String): Portfolio?
}
