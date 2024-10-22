package dto;

import java.util.Map;

public record CurrenciesRateDto(Map<String, String> conversion_rates) {}
