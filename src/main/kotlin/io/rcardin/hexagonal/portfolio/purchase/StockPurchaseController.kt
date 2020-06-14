package io.rcardin.hexagonal.portfolio.purchase

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StockPurchaseController(private val useCase: StockPurchaseUseCase) {

    @PutMapping("/portfolios/{portfolio}/stocks/{stock}")
    suspend fun buyStock(@PathVariable portfolio: String,
                         @PathVariable stock: String,
                         @RequestBody quantity: Long): ResponseEntity<Any> {
        val command = StockPurchaseUseCase.StockPurchaseCommand(portfolio, stock, quantity)
        return if (useCase.buy(command)) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}