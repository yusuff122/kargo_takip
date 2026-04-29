package com.yusuf.kargotakip.core.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetails handleBusinessException(BusinessException businessException) {
        return new ProblemDetails(businessException.getMessage());
    }

}
