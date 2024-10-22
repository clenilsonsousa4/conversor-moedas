package enums;

import java.util.ArrayList;
import java.util.List;

public enum CurrencyCodesEnum {
    ARGENTINE_PESO("ARS", "Argentine Peso"),
    BOLIVIAN_BOLIVIANO("BOB", "Bolivian Boliviano"),
    BRAZILIAN_REAL("BRL", "Brazilian Real"),
    CHILEAN_PESO("CLP", "Chilean Peso"),
    COLOMBIAN_PESO("COP", "Colombian Peso"),
    DOLLAR("USD", "US Dollar");

    private final String code;
    private final String description;

    CurrencyCodesEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static String getCodeByDescription(String description) {
        for (CurrencyCodesEnum currencyCodesEnum : CurrencyCodesEnum.values()) {
            if (currencyCodesEnum.getDescription().equals(description)) {
                return currencyCodesEnum.getCode();
            }
        }

        return null;
    }

    public static List<String> getAllCodes() {
        List<String> currenciesCodes = new ArrayList<>();
        for (CurrencyCodesEnum currencyCode : CurrencyCodesEnum.values()) {
            currenciesCodes.add(currencyCode.getCode());
        }
        return currenciesCodes;
    }

    public static List<String> getAllDescriptions() {
        List<String> currenciesDescriptions = new ArrayList<>();
        for (CurrencyCodesEnum currencyCode : CurrencyCodesEnum.values()) {
            currenciesDescriptions.add(currencyCode.getDescription());
        }
        return currenciesDescriptions;
    }
}
