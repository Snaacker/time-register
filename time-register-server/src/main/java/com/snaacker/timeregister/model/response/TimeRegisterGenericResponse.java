package com.snaacker.timeregister.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
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

    public TimeRegisterGenericResponse(List<T> genericObject, int pageSize, int offset) {
        this.genericObject = genericObject;
        this.pageSize = pageSize;
        this.offset = offset;
        this.total = genericObject.size();
    }
}
