package com.bsuir.kpp;

import com.bsuir.kpp.service.CounterService;
import com.bsuir.kpp.service.LogicService;
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
import java.util.*;

@Validated
@RestController
public class GenerationController {
    private static final Logger logger = LogManager.getLogger(GenerationController.class);

    @Autowired
    private NumbersCache hashMap;

    @GetMapping("/less")
    public GeneratedNumber generateLess(@RequestParam(value = "number", defaultValue = "0") @Min(0) Integer number) {
        new Thread(CounterService::increment).start();
        if (hashMap.containsKeyInHashMapLess(number)) {
            return hashMap.getParametersLess(number);
        } else {
            GeneratedNumber result = LogicService.findLess(number);
            hashMap.addToMapLess(number, result);
            logger.info("Success request get random number less");
            return result;
        }
    }

    @GetMapping("/more")
    public GeneratedNumber generateMore(@RequestParam(value = "number", defaultValue = "0") @Min(0) Integer number) {
        new Thread(CounterService::increment).start();
        if (hashMap.containsKeyInHashMapMore(number)) {
            return hashMap.getParametersMore(number);
        } else {
            GeneratedNumber result = LogicService.findMore(number);
            hashMap.addToMapMore(number, result);
            logger.info("Success request get random number more");
            return result;
        }
    }

    @PostMapping("/more")
    public ResponseEntity<?> calculateBulkForMore(@Valid @RequestBody List<Integer> bodyList) {
        if (bodyList.isEmpty()) {
            new ResponseEntity<>(new ResultDto(), HttpStatus.OK);
        }
        List<GeneratedNumber> resultMoreList = new LinkedList<>();
        bodyList.forEach(currentElem -> resultMoreList.add(LogicService.findMore(currentElem)));

        logger.info("Successfully postMapping");
        ResultDto dto = new ResultDto();
        if (!resultMoreList.isEmpty()) {
            dto.setList(resultMoreList);
            OptionalDouble opDouble = resultMoreList.stream().map(GeneratedNumber::getValue).mapToInt(Integer::intValue).average();
            if (opDouble.isPresent()) {
                dto.setAverageResult(opDouble.getAsDouble());
            }
            OptionalInt opInt = resultMoreList.stream().map(GeneratedNumber::getValue).mapToInt(Integer::intValue).max();
            if (opInt.isPresent()) {
                dto.setMaxResult(opInt.getAsInt());
            }
            opInt = resultMoreList.stream().map(GeneratedNumber::getValue).mapToInt(Integer::intValue).min();
            if (opInt.isPresent()) {
                dto.setMinResult(opInt.getAsInt());
            }
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/less")
    public ResponseEntity<?> calculateBulkForLess(@Valid @RequestBody List<Integer> bodyList) {
        if (bodyList.isEmpty()) {
            new ResponseEntity<>(new ResultDto(), HttpStatus.OK);
        }
        List<GeneratedNumber> resultMoreList = new LinkedList<>();
        bodyList.forEach(currentElem -> resultMoreList.add(LogicService.findLess(currentElem)));

        logger.info("Successfully postMapping");
        ResultDto dto = new ResultDto();
        if (!resultMoreList.isEmpty()) {
            dto.setList(resultMoreList);
            OptionalDouble opDouble = resultMoreList.stream().map(GeneratedNumber::getValue).mapToInt(Integer::intValue).average();
            if (opDouble.isPresent()) {
                dto.setAverageResult(opDouble.getAsDouble());
            }
            OptionalInt opInt = resultMoreList.stream().map(GeneratedNumber::getValue).mapToInt(Integer::intValue).max();
            if (opInt.isPresent()) {
                dto.setMaxResult(opInt.getAsInt());
            }
            opInt = resultMoreList.stream().map(GeneratedNumber::getValue).mapToInt(Integer::intValue).min();
            if (opInt.isPresent()) {
                dto.setMinResult(opInt.getAsInt());
            }
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
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
