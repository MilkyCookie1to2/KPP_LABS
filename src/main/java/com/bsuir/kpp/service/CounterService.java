package com.bsuir.kpp.service;

public class CounterService {
    private static int counter;

    public static synchronized void increment() {
        ++counter;
    }

    public static int getCounter() {
        return counter;
    }
}
