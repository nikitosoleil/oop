package com.knu.lab3.exception.handler;

import com.knu.lab3.dto.ErrorInfo;
import com.knu.lab3.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            BillNotFound.class,
            ServiceNotFound.class,
            UserNotFound.class
    })
    @ResponseBody
    public ErrorInfo notFoundExceptionHandler(Exception e) {
        return new ErrorInfo().setTimestamp(System.currentTimeMillis())
                .setMessage(e.getMessage())
                .setDeveloperMessage(e.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BillAlreadyPaid.class,
            UserIsBlocked.class
    })
    @ResponseBody
    public ErrorInfo badRequestExceptionHandler(Exception e) {
        return new ErrorInfo().setTimestamp(System.currentTimeMillis())
                .setMessage(e.getMessage())
                .setDeveloperMessage(e.toString());
    }
}
