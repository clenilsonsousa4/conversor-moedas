package services;

import com.google.gson.Gson;
import dto.CurrenciesRateDto;
import enums.CurrencyCodesEnum;
import exceptions.ApiRequestErrorException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class CurrencyExchangeService {
    private final String apiUrl = System.getenv("API_URL");
    private final String apiKey = System.getenv("API_KEY");

    public String getActualCurrenciesRates(String baseCurrencyCode) throws ApiRequestErrorException {
        String url = apiUrl + "latest/" + baseCurrencyCode;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Authorization", apiKey).build();
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new ApiRequestErrorException();
        }

        return response.body();
    }

    public Map<String, Double> getCurrenciesRates (String baseCurrencyCode) {
        Map<String, Double> currenciesRate = new HashMap<>();
        Gson gson = new Gson();
        CurrenciesRateDto currencyExchangeDto;

        try {
            currencyExchangeDto = gson.fromJson(getActualCurrenciesRates(baseCurrencyCode), CurrenciesRateDto.class);
        } catch (ApiRequestErrorException e) {
            System.out.println(e.getMessage());
            return currenciesRate;
        }

        for (String currencyCode : CurrencyCodesEnum.getAllCodes()) {
            if(!currencyCode.equals(baseCurrencyCode) && currencyExchangeDto.conversion_rates().containsKey(currencyCode)) {
                currenciesRate.put(currencyCode, Double.valueOf(currencyExchangeDto.conversion_rates().get(currencyCode)));
            }
        }

        return currenciesRate;
    }
}
