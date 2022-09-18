package com.example.dividendstocks.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class BSEStock {
    private String companyName;
    private String companyISIN;
}
