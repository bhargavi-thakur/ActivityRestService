package com.practice.guide.exception.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Map;

@Builder
@Getter
public class ErrorMessage{

    private Instant timeStamp;
    private int status;
    private String message;
    private Map<String,String> fieldErrors;

}

