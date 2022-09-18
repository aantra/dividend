package com.example.dividendstocks.Controller;

import com.example.dividendstocks.Dto.DividendDto;
import com.example.dividendstocks.Model.BSEStock;
import com.example.dividendstocks.Model.Dividend;
import com.example.dividendstocks.Model.NSEStock;
import com.example.dividendstocks.Model.Quote;
import com.example.dividendstocks.Service.DividendService;
import com.example.dividendstocks.Service.StockService;
import com.example.dividendstocks.Util;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/dividend")
public class DividendController {

    @Autowired
    private DividendService dividendService;

    @GetMapping("/all")
    public ResponseEntity<Dividend> getAllDividendStocks() throws IOException, InterruptedException, JSONException {
        final List<Dividend> allDividendStocks = dividendService.getAllDividendStocks();
        return new ResponseEntity(allDividendStocks,
                HttpStatus.OK);
    }

    @GetMapping("/id/{companyName}")
    public ResponseEntity<Quote> getDividendStock(@PathVariable String companyName) throws IOException, InterruptedException, JSONException {
        final Quote stock = dividendService.getDividendStock(companyName);
        return new ResponseEntity(stock,
                HttpStatus.OK);
    }

    @GetMapping("/latest")
    public ResponseEntity<DividendDto> getLatestDividendStocks() throws IOException, InterruptedException, JSONException {
        final List<DividendDto> allDividendStocks = dividendService.getLatestDividend();
        return new ResponseEntity(allDividendStocks,
                HttpStatus.OK);
    }
}
