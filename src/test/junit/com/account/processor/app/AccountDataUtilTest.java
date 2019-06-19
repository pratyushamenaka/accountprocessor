package com.account.processor.app;

import org.junit.Assert;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Test file to verify AccountDataUtil
 */
public class AccountDataUtilTest {

   @Test
    public void testDateConversion() throws ParseException {
       String dateString = "20/10/2018 12:00:00";
       Date convertedDate = AccountDataUtil.convertDateTime(dateString);
       SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
       Date expectedDate = formatter.parse(dateString);
       Assert.assertEquals(expectedDate, convertedDate);
   }

    @Test(expected = ParseException.class)
    public void testEmptyDate() throws ParseException {
        AccountDataUtil.convertDateTime("");
    }

    @Test
    public void processCSVFileTest() {
        AccountDataUtil tester = new AccountDataUtil();
        List<AccountData> expectedList = new ArrayList<>();
        expectedList.add(new AccountData("TX10001","ACC334455","ACC778899", "20/10/2018 12:47:55", 25.00, "PAYMENT",""));
        expectedList.add(new AccountData("TX10002","ACC334455","ACC998877", "20/10/2018 17:33:43", 10.50, "PAYMENT",""));
        expectedList.add(new AccountData("TX10003","ACC998877","ACC778899", "20/10/2018 18:00:00", 5.00, "PAYMENT",""));
        expectedList.add(new AccountData("TX10004","ACC334455","ACC998877", "20/10/2018 19:45:00", 10.50, "REVERSAL","TX10002"));
        expectedList.add(new AccountData("TX10005","ACC334455","ACC778899", "21/10/2018 09:30:00", 7.25, "PAYMENT",""));

        List<AccountData> actualList = tester.processCSVFile();
        Assert.assertEquals(expectedList.size(), actualList.size());
    }

    @Test
    public void calculateRelativeBalance() throws ParseException {
        AccountDataUtil tester = new AccountDataUtil();
        List<AccountData> acctList = tester.processCSVFile();
        tester.calculateRelativeBalance(acctList, "ACC334455", AccountDataUtil.convertDateTime("20/10/2018 12:00:00"), AccountDataUtil.convertDateTime("20/10/2018 19:00:00"));
    }

    @Test(expected = ParseException.class)
    public void calculateRelBalEmptyFromTime() throws ParseException {
        AccountDataUtil tester = new AccountDataUtil();
        List<AccountData> acctList = tester.processCSVFile();
        tester.calculateRelativeBalance(acctList, "ACC334455", AccountDataUtil.convertDateTime(""), AccountDataUtil.convertDateTime("20/10/2018 19:00:00"));
    }

    @Test(expected = ParseException.class)
    public void calculateRelBalEmptyToTime() throws ParseException {
        AccountDataUtil tester = new AccountDataUtil();
        List<AccountData> acctList = tester.processCSVFile();
        tester.calculateRelativeBalance(acctList, "ACC334455", AccountDataUtil.convertDateTime("20/10/2018 12:00:00"), AccountDataUtil.convertDateTime(""));
    }

    @Test(expected = InvalidParameterException.class)
    public void calculateRelBalEmptyInputAcctNum() throws ParseException {
        AccountDataUtil tester = new AccountDataUtil();
        List<AccountData> acctList = tester.processCSVFile();
        tester.calculateRelativeBalance(acctList, "", AccountDataUtil.convertDateTime("20/10/2018 12:00:00"), AccountDataUtil.convertDateTime("20/10/2018 19:00:00"));
    }


}


