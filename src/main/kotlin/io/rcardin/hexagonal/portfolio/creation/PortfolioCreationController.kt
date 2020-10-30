package io.rcardin.hexagonal.portfolio.creation

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class PortfolioCreationController(private val useCase: PortfolioCreationUseCase) {

    @PostMapping("/portfolios")
    suspend fun createPortfolio(@RequestBody name: String): ResponseEntity<Any> {
        val command = PortfolioCreationUseCase.PortfolioCreationCommand(name)
        val created = useCase.createPortfolio(command)
        return if (created) {
            ResponseEntity.created(URI.create("/portfolios/$name")).build()
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }
}
