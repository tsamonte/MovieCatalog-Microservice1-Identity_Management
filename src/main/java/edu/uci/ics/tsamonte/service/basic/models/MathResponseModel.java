package edu.uci.ics.tsamonte.service.basic.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MathResponseModel {

    @JsonProperty(value = "resultCode", required = true)
    private int resultCode;

    @JsonProperty(value = "message", required = true)
    private String message;

    @JsonProperty(value = "value")
    private Integer value;

    @JsonCreator
    public MathResponseModel(@JsonProperty(value = "resultCode", required = true) int resultCode,
                             @JsonProperty(value = "message", required = true) String message,
                             @JsonProperty(value = "value") Integer value) {
        this.resultCode = resultCode;
        this.message = message;
        this.value = value;
    }

    @JsonProperty(value = "resultCode")
    public int getResultCode() {
        return resultCode;
    }

    @JsonProperty(value = "message")
    public String getMessage() {
        return message;
    }

    @JsonProperty(value = "value")
    public Integer getValue() {
        return value;
    }
}
