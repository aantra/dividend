package com.example.dividendstocks.Service;

import com.example.dividendstocks.Dto.DividendDto;
import com.example.dividendstocks.Model.Dividend;
import com.example.dividendstocks.Model.Quote;
import com.example.dividendstocks.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DividendService {

    List<Dividend> rawList;
    Util util;

    public DividendService() {
        this.rawList = getAllDividendStocks();
        this.util = new Util();
    }

    private void getCompanySymbols() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://indian-stock-dividends-api.p.rapidapi.com/stocks/latest/dividends"))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            final HttpResponse<String> httpResponse = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<Dividend> getAllDividendStocks() {
        ArrayList<Dividend> dividends = new ArrayList<>();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://indian-stock-dividends-api.p.rapidapi.com/stocks/latest/dividends"))
                    .header("X-RapidAPI-Key", "5a45ef9916msh47bcf420f258efcp198c05jsn02fab1c18f28")
                    .header("X-RapidAPI-Host", "indian-stock-dividends-api.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            final HttpResponse<String> httpResponse = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());


            Gson gson = new Gson();
            Type userListType = new TypeToken<ArrayList<Dividend>>() {
            }.getType();

            dividends = gson.fromJson(httpResponse.body(), userListType);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dividends;
    }

    public Quote getDividendStock(String companySymbol) throws IOException, InterruptedException, JSONException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/nse/get_quote_info?companyName=" + companySymbol))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        final HttpResponse<String> httpResponse = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonObject = new JSONObject(httpResponse.body());

        Gson gson = new Gson();
        final JSONArray data = jsonObject.getJSONArray("data");
        final Quote quote = gson.fromJson(String.valueOf(data.get(0)), Quote.class);
        return quote;
    }

    private List<Dividend> processDividend(List<Dividend> dividendList) {
        List<Dividend> validDividends = new ArrayList<>();
        try {

            for (Dividend dividend : dividendList) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
                String exDividend = dividend.getExDividendDate();
                Date currDate = new Date(System.currentTimeMillis());
                final String format = simpleDateFormat.format(currDate);

                Date date1 = simpleDateFormat.parse(exDividend);
                Date date2 = simpleDateFormat.parse(format);

                if (date1.compareTo(date2) > 0) {
                    validDividends.add(dividend);
                }

            }

        } catch (ParseException e) {
            e.getMessage();
        }
        return validDividends;
    }

    private Quote getStockQuoteSearchByKeyword(String key) {
        Quote quote = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/nse/search_stocks?keyword=" + key))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            final HttpResponse<String> httpResponse = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject = new JSONObject(httpResponse.body());

            Gson gson = new Gson();
            final JSONArray data = jsonObject.getJSONArray("data");
             quote = gson.fromJson(String.valueOf(data.get(0)), Quote.class);
            return quote;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quote;
    }

    public List<DividendDto> getLatestDividend() throws JSONException, IOException, InterruptedException {
        final List<DividendDto> dividendDtos = new ArrayList<>();

        final List<Dividend> dividends = processDividend(rawList);
        for (Dividend d : dividends) {
            // TODO: company name should be consitent across all platforms
            String companySymbol = util.getNseMap().get(d.getCompanyName());
            if (companySymbol != null) {
                final Quote quote = getStockQuoteSearchByKeyword(companySymbol);
                System.out.println(quote);
                DividendDto dto = DividendDto.builder().companyName(d.getCompanyName())
                        .currentPrice(quote.getPriceBandUpper())
                        .dividendPercent(d.getPercent()+"%")
                        .exDividendDate(d.getExDividendDate()).build();
                dividendDtos.add(dto);
            }
            else {
                DividendDto dto = DividendDto.builder().companyName(d.getCompanyName())
                        .dividendPercent(d.getPercent()+"%")
                        .exDividendDate(d.getExDividendDate()).build();
                dividendDtos.add(dto);
            }
        }
        return dividendDtos;
    }

}
