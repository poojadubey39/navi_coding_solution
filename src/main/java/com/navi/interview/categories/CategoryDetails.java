package com.navi.interview.categories;

import java.util.HashMap;
import java.util.Map;

import static com.navi.interview.constants.Constants.INITIAL_INVESTMENT_MONTH;

public class CategoryDetails {

    private double allocation;
    private int sip;
    private int initialInvestment;
    private final Map<String, Integer> balanceSheet;
    private String lastUpdatedMonth;

    public CategoryDetails(int initialInvestment, double allocation) {
        this.initialInvestment = initialInvestment;
        this.allocation = allocation;
        balanceSheet = new HashMap<>();
        balanceSheet.put(INITIAL_INVESTMENT_MONTH, initialInvestment);
        lastUpdatedMonth = INITIAL_INVESTMENT_MONTH;
    }

    public double getAllocation() {
        return allocation;
    }

    public void setAllocation(double allocation) {
        this.allocation = allocation;
    }

    public Integer getSip() {
        return sip;
    }

    public void setSip(Integer sip) {
        this.sip = sip;
    }

    public int getInitialInvestment() {
        return initialInvestment;
    }

    public void setInitialInvestment(Integer initialInvestment) {
        this.initialInvestment = initialInvestment;
    }

    public int getBalance(String month) {
        return balanceSheet.get(month.toUpperCase());
    }

    public void updateBalance(String month, Integer balance) {
        this.balanceSheet.put(month.toUpperCase(), balance);
    }

    public void updateRebalancedAmount(Integer balance) {
        this.balanceSheet.put(lastUpdatedMonth.toUpperCase(), balance);
    }

    public Integer getBalance() {
        return balanceSheet.get(lastUpdatedMonth);
    }

    public String getLastUpdatedMonth() {
        return lastUpdatedMonth;
    }

    public void setLastUpdatedMonth(String lastUpdatedMonth) {
        this.lastUpdatedMonth = lastUpdatedMonth.toUpperCase();
    }

    public int getTotalTransactions() {
        return balanceSheet.size();
    }
}
