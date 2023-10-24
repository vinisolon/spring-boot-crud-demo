package br.com.vinisolon.application.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(BusinessRuleException.class)
    private ResponseEntity<ProblemDetail> handleBusinessRuleException(BusinessRuleException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildProblemDetail(HttpStatus.BAD_REQUEST, e, request.getRequestURI()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<ProblemDetail> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildProblemDetail(HttpStatus.BAD_REQUEST, e, request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ProblemDetail> handleException(Exception e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, e, request.getRequestURI()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    private ResponseEntity<ProblemDetail> handleDataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, e, request.getRequestURI()));
    }

    private ProblemDetail buildProblemDetail(HttpStatus httpStatus, Exception e, String path) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, e.getMessage());
        problemDetail.setInstance(URI.create(path));
        return problemDetail;
    }

}
