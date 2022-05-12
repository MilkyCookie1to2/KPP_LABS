package com.bsuir.kpp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResultDto {
    @JsonProperty
    private List<GeneratedNumber> resultMoreList;
    @JsonProperty
    private Double averageResult;
    @JsonProperty
    private Integer maxResult;
    @JsonProperty
    private Integer minResult;

    public void setList(List<GeneratedNumber> resultMoreList) {
        this.resultMoreList = resultMoreList;
    }

    public void setAverageResult(Double averageResult) {
        this.averageResult = averageResult;
    }

    public void setMaxResult(Integer maxResult) {
        this.maxResult = maxResult;
    }

    public void setMinResult(Integer minResult) {
        this.minResult = minResult;
    }
}
