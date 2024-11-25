package ru.yuriy.propertyrental.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.yuriy.propertyrental.util.exceptions.NotFoundException;
import ru.yuriy.propertyrental.util.response_body.ErrorResponse;

import java.sql.Timestamp;

@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exc)
    {
        return new ResponseEntity<>(createMessage(HttpStatus.NOT_FOUND, exc),
                HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request)
    {
        ErrorResponse errorResponse = createMessage(HttpStatus.BAD_REQUEST, ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    public static ErrorResponse createMessage(HttpStatus status, Throwable throwable)
    {
        return ErrorResponse.builder()
                .status(status.getReasonPhrase())
                .code(status.value())
                .errorMessage(throwable.getMessage())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}