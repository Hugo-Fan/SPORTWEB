package com.hugo.blog.model.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SportListParam {

    @NotBlank
    private String ActionGroup;
    @NotBlank
    private String timeInput;
    @NotBlank
    private String sumResult;
}
