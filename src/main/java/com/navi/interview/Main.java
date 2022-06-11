package com.navi.interview;

import com.navi.interview.service.Portfolio;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import static com.navi.interview.constants.Constants.*;

public class Main {
    private static Portfolio portfolio;

    private static void processInput(String inputCommand) {
        String[] inputData = inputCommand.split("\\s+");
        try {
            switch (inputData[0]) {
                case ALLOCATE:
                    portfolio = Portfolio.createPortfolio(inputData);
                    break;
                case SIP:
                    portfolio.setSIP(inputData);
                    break;
                case CHANGE:
                    portfolio.change(inputData);
                    break;
                case BALANCE:
                    System.out.println(portfolio.getBalance(inputData[1]));
                    break;
                case REBALANCE:
                    System.out.println(portfolio.reBalance());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream(args[0]);
            Scanner sc = new Scanner(fis); // file to be scanned
            while (sc.hasNextLine()) {
                String inputCommand = sc.nextLine();
                processInput(inputCommand.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
