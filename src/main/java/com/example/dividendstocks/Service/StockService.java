package com.example.dividendstocks.Service;

import com.example.dividendstocks.Model.BSEStock;
import com.example.dividendstocks.Model.NSEStock;
import com.example.dividendstocks.Util;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockService {
    private Util util;

    public StockService() {
        this.util = new Util();
    }

    public List<NSEStock> getAllNseStocks(){
        List<NSEStock> nseStocks = new ArrayList<>();
        final Map<String, String> map = util.getNseMap();
        for (Map.Entry<String, String> entry: map.entrySet()){
            nseStocks.add(new NSEStock(entry.getKey(), entry.getValue()));
        }
        return nseStocks;
    }

    public List<BSEStock> getAllBseStocks(){
        List<BSEStock> bseStocks = new ArrayList<>();
        final Map<String, String> map = util.getNseMap();
        for (Map.Entry<String, String> entry: map.entrySet()){
            bseStocks.add(new BSEStock(entry.getKey(), entry.getValue()));
        }
        return bseStocks;
    }

    public List<BSEStock> findBseStock(String key){
        List<BSEStock> possibleStocks = new ArrayList<>();

        for (Map.Entry<String, String> entry:util.getBseMap().entrySet()){
            if(entry.getKey().toLowerCase().contains(key)){
                possibleStocks.add(new BSEStock(entry.getKey(), entry.getValue()));
            }
        }
        return possibleStocks;
    }

    public List<NSEStock> findNseStock(String key){
        List<NSEStock> possibleStocks = new ArrayList<>();

        for (Map.Entry<String, String> entry:util.getNseMap().entrySet()){
            if(entry.getKey().toLowerCase().contains(key)){
                possibleStocks.add(new NSEStock(entry.getKey(), entry.getValue()));
            }
        }
        return possibleStocks;
    }
}
