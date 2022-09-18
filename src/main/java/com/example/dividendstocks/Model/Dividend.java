package com.example.dividendstocks.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Dividend {
    String exDividendDate;
    String companyName;
    String type;
    String percent;
}
