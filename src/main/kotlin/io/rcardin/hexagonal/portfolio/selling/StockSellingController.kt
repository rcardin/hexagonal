package io.rcardin.hexagonal.portfolio.selling

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class StockSellingController(private val useCase: StockSellingUseCase) {

    @DeleteMapping("/portfolios/{portfolio}/stocks/{stock}")
    suspend fun sellStock(@PathVariable portfolio: String,
                         @PathVariable stock: String,
                         @RequestParam quantity: Long): ResponseEntity<Any> {
        val command = StockSellingUseCase.StockSellingCommand(portfolio, stock, quantity)
        return if (useCase.sell(command)) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.badRequest().build()
        }
    }
}