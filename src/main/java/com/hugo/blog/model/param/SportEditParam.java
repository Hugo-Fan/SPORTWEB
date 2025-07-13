package com.hugo.blog.model.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SportEditParam {
    @NotBlank
    private String actionGroup;
    @NotBlank
    private String date;
    @NotBlank
    private String sports;
    @NotNull
    private Integer user_id;
    @NotNull
    private Integer sport_id;
}
