package com.dulfinne.configurator.exception

import com.dulfinne.configurator.util.ExceptionMessages
import com.dulfinne.configurator.util.ValidationMessages
import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException


@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(ex: ResponseStatusException): ResponseEntity<ErrorResponse> {
        ex.printStackTrace()
        val status = HttpStatus.valueOf(ex.statusCode.value())
        val errorResponse = ErrorResponse(status, ex.reason!!)
        return ResponseEntity.status(status).body(errorResponse)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(ex: EntityNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse =
            ErrorResponse(HttpStatus.NOT_FOUND, ex.message ?: ExceptionMessages.DEFAULT_ENTITY_NOT_FOUND)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(EntityExistsException::class)
    fun handleEntityExistsException(ex: EntityExistsException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(HttpStatus.CONFLICT, ex.message ?: ExceptionMessages.DEFAULT_ENTITY_EXISTS)
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
       ex.printStackTrace()
        val errors = ex.bindingResult.fieldErrors.associate { fieldError ->
            fieldError.field to (fieldError.defaultMessage ?: ValidationMessages.DEFAULT_MESSAGE)
        }
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(ex: ConstraintViolationException): ResponseEntity<Map<String, String>> {
        ex.printStackTrace()
        val errors = ex.constraintViolations.associate { violation ->
            violation.propertyPath.toString() to (violation.message ?: ValidationMessages.DEFAULT_MESSAGE)
        }
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleUnknownException(ex: Exception): ResponseEntity<ErrorResponse> {
        ex.printStackTrace()
        val errorResponse = ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionMessages.UNKNOWN_ERROR)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }
}