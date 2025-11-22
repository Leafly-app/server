package com.hansung.leafly.domain.bookreview.web.dto;

import java.time.LocalDate;
import java.util.List;

public record ReviewListRes (
        int count,
        List<ReviewRes> reviews
){

}
