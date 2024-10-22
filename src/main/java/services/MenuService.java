package services;

import enums.CurrencyCodesEnum;
import exceptions.FinishedByUserException;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuService {
    private Map<String, Double> currenciesRate;
    private List<String> currenciesDescriptions;
    private String baseCurrencyCode;
    private String targetCurrencyCode;
    private CurrencyExchangeService currencyExchangeService;

    public void startProcess() {
        currencyExchangeService = new CurrencyExchangeService();
        while (true) {
            try {
                currenciesDescriptions = CurrencyCodesEnum.getAllDescriptions();
                int selectedOption = selectOriginCurrency();

                currenciesDescriptions.remove(selectedOption - 1);
                getCurrenciesRates(selectedOption);

                selectedOption = selectTargetCurrency();
                targetCurrencyCode = CurrencyCodesEnum.getCodeByDescription(currenciesDescriptions.get(selectedOption - 1));
                realizeExchange(getValueToExchange());
            } catch (FinishedByUserException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    private int selectOriginCurrency() throws FinishedByUserException {
        return selectCurrencyOption("Select The Origin Currency For Exchange");
    }

    private int selectTargetCurrency() throws FinishedByUserException {
        return selectCurrencyOption("Select The Target Currency For Exchange");
    }

    private int selectCurrencyOption(String message) throws FinishedByUserException {
        Scanner scanner = new Scanner(System.in);
        int selectedOption = -1;
        while (selectedOption == -1) {
            try {
                showCurrencyOptions(message);
                selectedOption = Integer.parseInt(scanner.nextLine());
                System.out.println("You Selected The Currency: " + currenciesDescriptions.get(selectedOption - 1) + "\n");

                if(selectedOption > CurrencyCodesEnum.getAllDescriptions().size()) {
                    System.out.println("\nInsert only the number of an available option\n");
                    selectedOption = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInsert only the number of an available option\n");
            }
        }

        if(selectedOption == 0) {
            throw new FinishedByUserException();
        }

        return selectedOption;
    }

    private double getValueToExchange() {
        Scanner scanner = new Scanner(System.in);
        double value;
        while (true) {
            System.out.println("Insert The Value To Be Exchanged");
            try {
                value = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Insert the Value To Be Exchanged With The Format 0.00\n");
            }
        }

        return value;
    }

    private void realizeExchange(double valueToBeExchanged) {
        valueToBeExchanged *= currenciesRate.get(targetCurrencyCode);
        System.out.printf("\nThe Value Exchanged To " + targetCurrencyCode + " Is Equivalent To $%.2f\n", valueToBeExchanged);
    }

    private void showCurrencyOptions(String message) {
        System.out.println(message);
        System.out.println("0 - Quit");
        int i = 1;
        for (String description : currenciesDescriptions) {
            System.out.println(i + " - " + description);
            i++;
        }
    }

    private void getCurrenciesRates(int selectedOption) {
        String selectedCurrencyCode = CurrencyCodesEnum.getAllCodes().get(selectedOption - 1);

        if(currenciesRate == null || !selectedCurrencyCode.equals(baseCurrencyCode)) {
            this.currenciesRate = currencyExchangeService.getCurrenciesRates(selectedCurrencyCode);
            this.baseCurrencyCode = selectedCurrencyCode;
        }
    }
}
