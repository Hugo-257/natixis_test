package com.natixis_test.backend.model.bodyRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;



public record UpdateTask(Boolean status) {
    @JsonCreator
    public UpdateTask(@JsonProperty("status") Boolean status) {
        this.status = status;
    }

}
