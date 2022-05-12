package com.bsuir.kpp.service;

import com.bsuir.kpp.GeneratedNumber;

public class LogicService {

    public static GeneratedNumber findMore(Integer number){
        return new GeneratedNumber((int) (Math.random() * 100 + number));
    }

    public static GeneratedNumber findLess(Integer number){
        return new GeneratedNumber((int) (Math.random() * (number + 1)));
    }
}
