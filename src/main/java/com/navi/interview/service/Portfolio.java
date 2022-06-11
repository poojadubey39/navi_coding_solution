package com.navi.interview.service;

import static com.navi.interview.constants.Constants.INITIAL_INVESTMENT_MONTH;
import static com.navi.interview.constants.Constants.CANNOT_REBALANCE;

import com.navi.interview.enums.Categories;
import com.navi.interview.categories.CategoryDetails;

import java.util.*;

public class Portfolio {

    private final Map<Categories, CategoryDetails> categoryDetailsMap;

    private Portfolio(Map<Categories, CategoryDetails> categoryDetailsMap) {
        this.categoryDetailsMap = categoryDetailsMap;
    }

    public void change(String[] changeData) {
        calculateChange(categoryDetailsMap.get(Categories.EQUITY), changeData[1], changeData[4]);
        calculateChange(categoryDetailsMap.get(Categories.DEBT), changeData[2], changeData[4]);
        calculateChange(categoryDetailsMap.get(Categories.GOLD), changeData[3], changeData[4]);

    }

    public List<String> getBalance(String month) {
        List<String> balance = new ArrayList<>();
        for (Categories category : Categories.values()) {
            balance.add(Integer.toString(categoryDetailsMap.get(category).getBalance(month)));
        }
        return balance;
    }

    public List<String> reBalance() {
        if (categoryDetailsMap.get(Categories.EQUITY).getTotalTransactions() < 6) {
            return Collections.singletonList(CANNOT_REBALANCE);
        }

        int portfolioBalance = 0;
        for (Categories category : Categories.values()) {
            CategoryDetails categoryDetails = categoryDetailsMap.get(category);
            portfolioBalance += categoryDetails.getBalance();
        }

        List<String> reBalancedAmounts = new ArrayList<>();
        for (Categories category : Categories.values()) {
            CategoryDetails categoryDetails = categoryDetailsMap.get(category);
            int rebalancedAmount = (int) (categoryDetails.getAllocation() * portfolioBalance) / 100;
            categoryDetails.updateRebalancedAmount(rebalancedAmount);
            reBalancedAmounts.add(Integer.toString(rebalancedAmount));

        }
        return reBalancedAmounts;
    }

    public void setSIP(String[] sip) {
        categoryDetailsMap.get(Categories.EQUITY).setSip(Integer.parseInt(sip[1]));
        categoryDetailsMap.get(Categories.DEBT).setSip(Integer.parseInt(sip[2]));
        categoryDetailsMap.get(Categories.GOLD).setSip(Integer.parseInt(sip[3]));
    }

    public static Portfolio createPortfolio(String[] initialInvestments) {
        int equityAmt = Integer.parseInt(initialInvestments[1]);
        int debtAmt = Integer.parseInt(initialInvestments[2]);
        int goldAmt = Integer.parseInt(initialInvestments[3]);

        int total = equityAmt + debtAmt + goldAmt;

        Map<Categories, CategoryDetails> categoryDetailsMap = new HashMap<>();
        categoryDetailsMap.put(Categories.EQUITY, new CategoryDetails(equityAmt, (double) (equityAmt * 100) / total));
        categoryDetailsMap.put(Categories.DEBT, new CategoryDetails(debtAmt, (double) (debtAmt * 100) / total));
        categoryDetailsMap.put(Categories.GOLD, new CategoryDetails(goldAmt, (double) (goldAmt * 100) / total));

        return new Portfolio(categoryDetailsMap);
    }

    private void calculateChange(CategoryDetails categoryDetails, String changeData, String month) {

        int newBalance;
        double changePercent = Double.parseDouble(changeData.replace("%", ""));
        if (Objects.equals(month, INITIAL_INVESTMENT_MONTH)) {
            newBalance = (int) (categoryDetails.getBalance() + categoryDetails.getBalance() * (changePercent / 100));
        } else {
            int tempBalance = categoryDetails.getBalance() + categoryDetails.getSip();
            newBalance = (int) (tempBalance + (tempBalance * (changePercent / 100)));
        }
        categoryDetails.updateBalance(month.toUpperCase(), newBalance);
        categoryDetails.setLastUpdatedMonth(month);
    }

}
