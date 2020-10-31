package io.rcardin.hexagonal.portfolio.adapter.`in`.web

import io.rcardin.hexagonal.portfolio.application.port.`in`.StockSellingUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class StockSellingController(private val useCase: StockSellingUseCase) {

    @DeleteMapping("/portfolios/{portfolio}/stocks/{stock}")
    suspend fun sellStock(
        @PathVariable portfolio: String,
        @PathVariable stock: String,
        @RequestParam quantity: Long
    ): ResponseEntity<Any> {
        val command = StockSellingUseCase.StockSellingCommand(portfolio, stock, quantity)
        return if (useCase.sell(command)) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.badRequest().build()
        }
    }
}
