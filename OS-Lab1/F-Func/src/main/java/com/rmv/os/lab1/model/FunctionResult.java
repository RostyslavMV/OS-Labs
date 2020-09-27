package com.rmv.os.lab1.model;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class FunctionResult implements Serializable {
    private String functionName;
    private Double result;
}
