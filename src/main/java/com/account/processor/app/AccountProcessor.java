package com.account.processor.app;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Main class - Account Processor
 */
public class AccountProcessor {

    public static void main(String[] args) throws ParseException {

        AccountDataUtil accountDataUtil = new AccountDataUtil();

        //transform the CSV file to POJO
        List<AccountData> accountDataList = accountDataUtil.processCSVFile();

        //read the input account details
        System.out.print("Enter the input accountId :::: ");

        Scanner in = new Scanner(System.in);
        String inputAccountNum = in.nextLine();
        System.out.println("accountId: " + inputAccountNum);
        System.out.print("Enter the Start Search Date and Time :::: ");

        String fromInputTime = in.nextLine();
        System.out.println("from: " + fromInputTime);
        Date fromInputDateTime = AccountDataUtil.convertDateTime(fromInputTime);

        System.out.print("Enter the End Search Date and Time :::: ");
        String toInputTime = in.nextLine();
        Date toInputDateTime = AccountDataUtil.convertDateTime(toInputTime);
        System.out.println("to: " + toInputTime);

        //method to calculate the relative account balance and number of transactions
        accountDataUtil.calculateRelativeBalance(accountDataList, inputAccountNum, fromInputDateTime, toInputDateTime);

    }


}
