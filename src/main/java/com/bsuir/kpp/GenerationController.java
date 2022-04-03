package com.bsuir.kpp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;

@Validated
@RestController
public class GenerationController {
    private static final Logger logger = LogManager.getLogger(GenerationController.class);

    @GetMapping("/less")
    public GeneratedNumber generateLess(@RequestParam(value = "number", defaultValue = "0") @Min(0) Integer number) {
        logger.info("Success request get random number less");
        return new GeneratedNumber((int) (Math.random() * (++number)));
    }

    @GetMapping("/more")
    public GeneratedNumber generateMore(@RequestParam(value = "number", defaultValue = "0") @Min(0) Integer number) {
        logger.info("Success request get random number more");
        return new GeneratedNumber((int) (Math.random() * 100 + number));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<String> errorEntryParams(MethodArgumentTypeMismatchException e){
        logger.error("Entry wrong params");
        return new ResponseEntity<>("<h1>ERROR 400: BAD REQUEST</h1><p>Parameter must be number!</p>", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<String> errorNegativeParams(ConstraintViolationException e){
        logger.error("Entry wrong params");
        return new ResponseEntity<>("<h1>ERROR 400: BAD REQUEST</h1><p>Parameter must be positive number!</p>", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<String> errorServer(RuntimeException e){
        logger.error("Something wrong with server");
        return new ResponseEntity<>("<h1>ERROR 500</h1>", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
