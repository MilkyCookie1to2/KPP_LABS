package com.bsuir.kpp;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CounterService{
    private static int counter;

    public static synchronized void increment() {
        ++counter;
    }

    public static int getCounter() {
        return counter;
    }
}
