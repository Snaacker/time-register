package com.snaacker.timeregister.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
@JsonIgnoreProperties(
        value = {"hibernateLazyInitializer", "handler"},
        ignoreUnknown = true)
public class TimeRegisterGenericResponse<T> {
    private List<T> genericObject;
    @JsonProperty("page_size")
    private int pageSize;
    @JsonProperty("offset")
    private int offset;

    @JsonProperty("total")
    private int total;

    TimeRegisterGenericResponse(){
        super();
    }

    public TimeRegisterGenericResponse(List<T> genericObject, int pageSize, int offset){
        this.genericObject = genericObject;
        this.pageSize = pageSize;
        this.offset = offset;
        this.total = genericObject.size();
    }
}
