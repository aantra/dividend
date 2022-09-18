package com.example.dividendstocks.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class DividendDto {
    private String companyName;
    private String currentPrice;
    private String dividendPercent;
    private String exDividendDate;

}
