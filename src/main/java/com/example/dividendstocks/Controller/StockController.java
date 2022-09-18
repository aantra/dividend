package com.example.dividendstocks.Controller;

import com.example.dividendstocks.Model.BSEStock;
import com.example.dividendstocks.Model.NSEStock;
import com.example.dividendstocks.Service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping("/nse/stocks")
    public List<NSEStock> getNSEStocks(){
        return stockService.getAllNseStocks();
    }

    @GetMapping("/bse/stocks")
    public List<BSEStock> getBSEStocks(){
        return stockService.getAllBseStocks();
    }

    @GetMapping("/bse/find/{name}")
    public List<BSEStock> getBSEStockSymbol(@PathVariable String name){
        return stockService.findBseStock(name);
    }

    @GetMapping("/nse/find/{name}")
    public List<NSEStock> getNSEStockSymbol(@PathVariable String name){
        return stockService.findNseStock(name);
    }

}
