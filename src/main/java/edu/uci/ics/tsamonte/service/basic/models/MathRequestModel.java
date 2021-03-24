package edu.uci.ics.tsamonte.service.basic.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MathRequestModel {

    @JsonProperty(value = "num_x", required = true)
    private Integer num_x;

    @JsonProperty(value = "num_y", required = true)
    private Integer num_y;

    @JsonProperty(value = "num_z", required = true)
    private Integer num_z;

    @JsonCreator
    public MathRequestModel(@JsonProperty(value = "num_x", required = true) Integer num_x,
                            @JsonProperty(value = "num_y", required = true) Integer num_y,
                            @JsonProperty(value = "num_z", required = true) Integer num_z) {
        this.num_x = num_x;
        this.num_y = num_y;
        this.num_z = num_z;
    }

    @JsonProperty("num_x")
    public Integer getNum_x() {
        return num_x;
    }

    @JsonProperty("num_y")
    public Integer getNum_y() {
        return num_y;
    }

    @JsonProperty("num_z")
    public Integer getNum_z() {
        return num_z;
    }
}
