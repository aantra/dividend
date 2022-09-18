package com.example.dividendstocks.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Getter
public class Quote implements Serializable {
    @SerializedName(value = "companyName")
    private String name;
    @SerializedName(value = "pricebandlower")
    private String priceBandLower;
    @SerializedName(value = "pricebandupper")
    private String priceBandUpper;
    @SerializedName(value = "exDate")
    private String exDate;
    @SerializedName(value = "symbol")
    private String companySymbol;

//    @JsonIgnoreProperties(value = {"tradedDate","symbol","bcEndDate","totalSellQuantity","applicableMargin" ,"adhocMargin","marketType", })
//    private String props;
}

