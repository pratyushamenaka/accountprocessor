package com.account.processor.app;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Util class
 */
public class AccountDataUtil {

    private static String REVERSAL_TYPE = "REVERSAL";
    private static String PAYMENT_TYPE = "PAYMENT";


    /**
     * convert String to date
     *
     * @param : String
     * @return : Date
     */
    public static Date convertDateTime(String dateString) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = formatter.parse(dateString);
        return date;
    }


    /**
     * process csv file to POJO
     *
     * @return : List
     */
    public List<AccountData> processCSVFile() {
        // file path AccountInput.csv file placed under resources folder
        // Getting ClassLoader obj
        ClassLoader classLoader = this.getClass().getClassLoader();
        // Getting resource(File) from class loader
        InputStream inputStream = classLoader.getResourceAsStream("AccountsInput.csv");
        CSVParser csvParser = null;

        List<AccountData> accountDataList = new ArrayList<>();
        try {
            //parse the csv file and convert it to AccountData object list
            csvParser = CSVParser.parse(inputStream, Charset.defaultCharset(), CSVFormat.DEFAULT.withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());
            Stream<CSVRecord> csvRecordStream = StreamSupport.stream(csvParser.spliterator(), false);
            accountDataList = csvRecordStream.map(CSVRecord::toMap).map(AccountData::new).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                csvParser.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return accountDataList;
    }


    /**
     * caluclate relative account balance & number of transactions
     *
     * @param accountDataList
     * @param inputAccountNum
     * @param fromInputTime
     * @param toInputTime
     */
    public void calculateRelativeBalance(List<AccountData> accountDataList, String inputAccountNum, Date fromInputTime, Date toInputTime) {
        //global variables for transactionCount and relativeAcctBalance
        int transactionsCount = 0;
        Double relativeAcctBal = 0.0;

        if (inputAccountNum.isEmpty()) {
            throw new InvalidParameterException();
        }

        // filter all the reversal transactions
        List<AccountData> reversalList = accountDataList.stream()
                .filter(accountData -> (REVERSAL_TYPE.equalsIgnoreCase(accountData.getTransactionType()) && null != accountData.getRelatedTransaction()))
                .collect(Collectors.toList());

        // filter payment records between the input from date and to date range and
        // remove the reversal related transactions from payments records list
        List<AccountData> paymentInRangeList = accountDataList.parallelStream()
                .filter(accountData -> (accountData.getCreatedAt().after(fromInputTime) && accountData.getCreatedAt().before(toInputTime)))
                .filter(filterReversal(reversalList)).collect(Collectors.toList());

        // traverse the filtered payment list to calculate relative Acct Balance & transactionCount
        for (AccountData accountData : paymentInRangeList) {
            if (inputAccountNum.equalsIgnoreCase(accountData.getFromAccountId())) {
                /* Condition : input account number is from account ID then increment transaction count and
                deduct the amount from relative account balance*/
                transactionsCount++;
                relativeAcctBal -= accountData.getAmount();

            } else if (inputAccountNum.equalsIgnoreCase(accountData.getToAccountId())) {
                     /* Condition : input account number is to account ID
                then reduce transaction count and
                add the amount to relative account balance */
                transactionsCount++;
                relativeAcctBal += accountData.getAmount();
            }
        }

        //Display the relative account balance and number of transactions
        System.out.println("Relative balance for the period is: $" + relativeAcctBal);
        System.out.println("Number of transactions included is: " + transactionsCount);
    }

    public static Predicate<AccountData> filterReversal(List<AccountData> reversalList) {
        return accountData -> checkReversalExist(accountData, reversalList);
    }

    public static boolean checkReversalExist(AccountData accountData, List<AccountData> reversalList) {
        boolean exists = true;
        for (AccountData acctData : reversalList) {
            if (acctData.getRelatedTransaction().equalsIgnoreCase(accountData.getTransactionId())) {
                exists = false;
            }
        }
        return exists;
    }
}
