package io.rcardin.hexagonal.portfolio.selling

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StockSellingController(private val useCase: StockSellingUseCase) {

    @DeleteMapping("/portfolios/{portfolio}/stocks/{stock}")
    suspend fun buyStock(@PathVariable portfolio: String,
                         @PathVariable stock: String,
                         @RequestBody quantity: Long): ResponseEntity<Any> {
        val command = StockSellingUseCase.StockSellingCommand(portfolio, stock, quantity)
        useCase.sell(command)
        // TODO Take a decision on return type
        return ResponseEntity.ok().build();
    }
}