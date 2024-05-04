package com.gml.exception;

import com.gml.entity.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice
{



        @ExceptionHandler(value=ResourceNotFoundException.class)
        public ResponseEntity<ResponseError> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
       ResponseError error= ResponseError.builder()
            .errorCode(ex.getCode())
            .message(ex.getMessage())
            .build();


            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }
    @ExceptionHandler(value=RequestException.class)
    public ResponseEntity<ResponseError> resourceNotFoundExceptionHandler(RequestException ex){
        ResponseError error= ResponseError.builder()
                .errorCode(ex.getCode())
                .message(ex.getMessage())
                .build();


        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
        ResponseError error= ResponseError.builder()
                .errorCode("004")
                .message((String) Objects.requireNonNull(ex.getDetailMessageArguments())[1])
                .build();


        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }


        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<String> handleRuntimeException(RuntimeException ex){
            //log.error("Exception caught in handleClientException :  {} " ,ex.getMessage(),  ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
