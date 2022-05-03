package com.bsuir.kpp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Validated
@RestController
public class GenerationController {
    private static final Logger logger = LogManager.getLogger(GenerationController.class);

    @Autowired
    private NumbersCache hashMap;

    @GetMapping("/less")
    public GeneratedNumber generateLess(@RequestParam(value = "number", defaultValue = "0") @Min(0) Integer number) {
        new Thread(CounterService::increment).start();
        if (hashMap.findByKeyInHashMapLess(number)) {
            return hashMap.getParametersLess(number);
        } else {
            GeneratedNumber result = new GeneratedNumber((int) (Math.random() * (number + 1)));
            hashMap.addToMapLess(number, result);
            logger.info("Success request get random number less");
            return result;
        }
    }

    @GetMapping("/more")
    public GeneratedNumber generateMore(@RequestParam(value = "number", defaultValue = "0") @Min(0) Integer number) {
        new Thread(CounterService::increment).start();
        if (hashMap.findByKeyInHashMapMore(number)) {
            return hashMap.getParametersMore(number);
        } else {
            GeneratedNumber result = new GeneratedNumber((int) (Math.random() * 100 + number));
            hashMap.addToMapMore(number, result);
            logger.info("Success request get random number more");
            return result;
        }
    }

    @PostMapping("/more")
    public ResponseEntity<?> calculateBulkForMore(@Valid @RequestBody List<Integer> bodyList) {
        List<Integer> resultMoreList = new LinkedList<>();
        bodyList.forEach((currentElem) -> {
            resultMoreList.add((int) (Math.random() * 100 + currentElem));
        });

        logger.info("Successfully postMapping");

        double averageResult = 0;
        if (!resultMoreList.isEmpty()) {
            averageResult = resultMoreList.stream().mapToInt(Integer::intValue).average().getAsDouble();
        }
        int maxResult = 0;
        if (!resultMoreList.isEmpty()) {
            maxResult = resultMoreList.stream().mapToInt(Integer::intValue).max().getAsInt();
        }
        int minResult = 0;
        if (!resultMoreList.isEmpty()) {
            minResult = resultMoreList.stream().mapToInt(Integer::intValue).min().getAsInt();
        }

        return new ResponseEntity<>(resultMoreList + "\nAverage: " + averageResult + "\nMax result: " +
                maxResult + "\nMin result: " + minResult, HttpStatus.OK);
    }

    @PostMapping("/less")
    public ResponseEntity<?> calculateBulkForLess(@Valid @RequestBody List<Integer> bodyList) {
        List<Integer> resultLessList = new LinkedList<>();
        bodyList.forEach((currentElem) -> {
            resultLessList.add((int) (Math.random() * (currentElem + 1)));
        });

        logger.info("Successfully postMapping");

        double averageResult = 0;
        if (!resultLessList.isEmpty()) {
            averageResult = resultLessList.stream().mapToInt(Integer::intValue).average().getAsDouble();
        }
        int maxResult = 0;
        if (!resultLessList.isEmpty()) {
            maxResult = resultLessList.stream().mapToInt(Integer::intValue).max().getAsInt();
        }
        int minResult = 0;
        if (!resultLessList.isEmpty()) {
            minResult = resultLessList.stream().mapToInt(Integer::intValue).min().getAsInt();
        }

        return new ResponseEntity<>(resultLessList + "\nAverage: " + averageResult + "\nMax result: " +
                maxResult + "\nMin result: " + minResult, HttpStatus.OK);
    }

    @GetMapping("/counter")
    public ResponseEntity<Integer> getCount(){
        return new ResponseEntity<>(CounterService.getCounter(), HttpStatus.OK);
    }

    @GetMapping("/cacheMore")
    public Map<Integer, GeneratedNumber> getCacheMore() {
        logger.info("Get cache more");
        return hashMap.getCacheMore();
    }

    @GetMapping("/cacheLess")
    public Map<Integer, GeneratedNumber> getCacheLess() {
        logger.info("Get cache less");
        return hashMap.getCacheLess();
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
        logger.error(e.getMessage());
        return new ResponseEntity<>("<h1>ERROR 500</h1>", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
