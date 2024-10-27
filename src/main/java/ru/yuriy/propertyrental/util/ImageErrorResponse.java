package ru.yuriy.propertyrental.util;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class ImageErrorResponse
{
    private String status;

    private Integer code;

    private String errorMessage;

    private Timestamp timestamp;
}