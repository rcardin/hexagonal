package io.rcardin.hexagonal.portfolio

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PortfolioRepository: CoroutineCrudRepository<Portfolio, String> {
}