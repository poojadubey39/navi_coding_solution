package com.navi.interview;

import com.navi.interview.service.Portfolio;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class PortfolioTest {

    private Portfolio portfolio;

    @Before
    public void setUp() {
        portfolio = Portfolio.createPortfolio(new String[]{"", "6000", "3000", "1000"});
    }

    @Test
    public void assertChangeForJan() {
        portfolio.change(new String[]{"CHANGE", "4.00%", "10.00%", "2.00%", "JANUARY"});
        List<String> data = portfolio.getBalance("JANUARY");
        List<String> expectedData = Arrays.asList("6240", "3300", "1020");
        assertEquals(data, expectedData);
    }

    @Test
    public void assertCannotRebalance() {
        List<String> data = portfolio.reBalance();
        assertEquals(data.get(0), "CANNOT_REBALANCE");
    }

    @Test
    public void assertCanRebalance() {
        portfolio.setSIP(new String[]{"", "2000", "1000", "500"});
        portfolio.change(new String[]{"CHANGE", "4.00%", "10.00%", "2.00%", "JANUARY"});
        portfolio.change(new String[]{"CHANGE", "-10.00%", "40.00%", "0.00%", "FEBRUARY"});
        portfolio.change(new String[]{"CHANGE", "12.50%", "12.50%", "12.50%", "MARCH"});
        portfolio.change(new String[]{"CHANGE", "8.00%", "-3.00%", "7.00%", "APRIL"});
        portfolio.change(new String[]{"CHANGE", "13.00%", "21.00%", "10.50%", "MAY"});
        portfolio.change(new String[]{"CHANGE", "10.00%", "8.00%", "-5.00%", "JUNE"});

        List<String> reBalance = portfolio.reBalance();
        List<String> expectedData = Arrays.asList("23619", "11809", "3936");
        assertEquals(expectedData, reBalance);
    }

    @Test
    public void assertBalanceMarch() {
        portfolio.setSIP(new String[]{"", "2000", "1000", "500"});
        portfolio.change(new String[]{"CHANGE", "4.00%", "10.00%", "2.00%", "JANUARY"});
        portfolio.change(new String[]{"CHANGE", "-10.00%", "40.00%", "0.00%", "FEBRUARY"});
        portfolio.change(new String[]{"CHANGE", "12.50%", "12.50%", "12.50%", "MARCH"});


        List<String> balance = portfolio.getBalance("MARCH");
        List<String> expectedData = Arrays.asList("10593", "7897", "2272");
        assertEquals(expectedData, balance);
    }
}
