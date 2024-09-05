package com.example.cum.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Builder
@Data
public class PagebleResponse<T> {
    private Integer total_page;
    private Integer page;
    private Integer limit;
    private Collection<T> data;
}
