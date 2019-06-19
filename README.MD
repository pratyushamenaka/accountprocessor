## Account Processor App

This project contains the code for reading a CSV file and display the Relative account balance and Number of transactions for 
the input account number within the time frame.

Environment - Java 8, apache commons csv, junit.

Contents – Source Files, Test Files and accountprocessor.jar file.

### Input CSV File

AccountInput.csv file is added with example input (mentioned in the coding excercise) and some extra account details for testing

### Input Parameters

accountId - account number
 
from - Start Search Date and Time eg: 20/10/2018 12:00:00

to - End Search Date and Time eg: 20/10/2018 19:00:00

### output 

Displays Relative account balance (negative or positive value) and number of transactions.

### how to run 

Please run the accountprocessor.jar file for program execution.

Sample test run

```
Enter the input accountId :::: ACC334455
accountId: ACC334455
Enter the Start Search Date and Time :::: 20/10/2018 12:00:00
from: 20/10/2018 12:00:00
Enter the End Search Date and Time :::: 20/10/2018 19:00:00
to: 20/10/2018 19:00:00
Relative balance for the period is: $-25.0
Number of transactions included is: 1
```




