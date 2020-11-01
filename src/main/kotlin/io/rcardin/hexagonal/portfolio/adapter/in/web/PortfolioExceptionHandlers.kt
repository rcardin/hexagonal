package io.rcardin.hexagonal.portfolio.adapter.`in`.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.valiktor.ConstraintViolationException

@ControllerAdvice
class PortfolioExceptionHandlers {
    @ExceptionHandler
    fun constraintViolationException(exception: ConstraintViolationException): ResponseEntity<Any> {
        val violations =
            exception.constraintViolations
                .joinToString(separator = "\n") { "${it.property}: ${it.constraint.name}" }
        return ResponseEntity
            .badRequest()
            .body(violations)
    }
}
