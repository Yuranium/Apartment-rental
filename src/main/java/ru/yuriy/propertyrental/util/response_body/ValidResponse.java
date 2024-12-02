package ru.yuriy.propertyrental.util.response_body;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class ValidResponse
{
    private String status;

    private Integer code;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Timestamp timestamp;

    private Map<String, List<String>> errorMessages;
}