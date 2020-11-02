package io.rcardin.hexagonal.portfolio.adapter.`in`.web

import io.rcardin.hexagonal.portfolio.application.port.`in`.PortfolioCreationUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.valiktor.ConstraintViolationException
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

    @ControllerAdvice(assignableTypes = [ PortfolioCreationController::class ])
    class PortfolioCreationExceptionHandlers {
        @ExceptionHandler
        fun constraintViolationException(exception: ConstraintViolationException):
            ResponseEntity<PortfolioError> {
                return ResponseEntity
                    .badRequest()
                    .body(
                        PortfolioError(
                            "EMPTY_PORTFOLIO_NAME",
                            "The name of the portfolio cannot be empty"
                        )
                    )
            }
    }
}
