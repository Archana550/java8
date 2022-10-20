package atm;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;



import model.Account;
import utility.ReadCard;

public class Menu {

static Random random = new Random();
static Logger log = LogManager.getLogger(Menu.class.getName());
public static final String EXIT = "Exit";

public static void main(String[] args) throws IOException, SQLException {

Scanner in = new Scanner(System.in);

log.log(Level.INFO, "************************************************");
log.log(Level.INFO, "*     Welcome to My Java Project ATM !!!       *");
log.log(Level.INFO, "************************************************");

log.info(" 1. Visit Bank ");
log.info(" 2. Use ATM ");
log.info(" 3. " + EXIT);

log.info("Enter your Choice: ");
int choice = in.nextInt();

switch (choice) {

case 1:
showBankMenu();
break;
case 2:

String[] detailslist = ReadCard.readcard();
log.info("--------after processing card-------------");

if (detailslist == null) {
log.warn("no not inserted properly.");
break;
}
String cardnumber = detailslist[0];
String cvv = detailslist[1];
int accountno = AccountDAO.findAccountNumber(cardnumber, cvv);
showAtmMenu(accountno);
break;
case 3:
log.info("Thankyou for using our java project Bank.");
System.exit(0);
break;
default:
log.info("Please make a valid choice!");

}

}

public static void showMainMenu() throws SQLException, IOException {
Scanner in = new Scanner(System.in);
do {
log.info("--------------------");
log.info(" 1. Visit Bank ");
log.info(" 2. Use ATM ");
log.info(" 3. " + EXIT);

log.info("Enter your Choice: ");
int choice = in.nextInt();

switch (choice) {

case 1:
showBankMenu();
break;
case 2:
String[] detailslist = ReadCard.readcard();
if (detailslist == null) {
log.warn("no not inserted properly.");
break;
}
// String accountno = detailslist[0];
String cardnumber = detailslist[0];
String cvv = detailslist[1];
int accountno = AccountDAO.findAccountNumber(cardnumber, cvv);
showAtmMenu(accountno);
break;
case 3:
log.info("Thankyou for using our java Project Bank.");
System.exit(0);
break;
default:
log.info("Please make a valid choice!");

}
} while (true);

}

private static void showAtmMenu(int accountno) throws SQLException, IOException {
Scanner in = new Scanner(System.in);
do {

log.info("---------------------------");
log.info(" 1. Generate Pin. ");
log.info(" 2. Change Pin ");
log.info(" 3. View Balance ");
log.info(" 4. Withdraw Amount ");
log.info(" 5. " + EXIT);

log.log(Level.INFO, "Enter Choice: ");

int atmchoice = in.nextInt();
switch (atmchoice) {

case 4:
withdraw(accountno);
break;
case 3:
checkBalance(accountno);
break;
case 1:
generatePin(accountno);
break;
case 2:
changePin(accountno);
break;
case 5:
log.info("Thankyou for using our java project ATM.");
showMainMenu();

break;
default:
log.info("Please make a valid choice.");

}
} while (true);

}

private static void withdraw(int accountno) throws SQLException {

Scanner in = new Scanner(System.in);

log.info("Enter  your pin:");
String pin = in.next();
log.log(Level.INFO, "Enter the amount you want to withdraw:");
log.log(Level.INFO, "Enter only  in multiples of 500");
int withdraw = in.nextInt();
AccountDAO.withdrawMoney(accountno, pin, withdraw);

}

private static void changePin(int accountno2) throws IOException, SQLException {

Scanner in = new Scanner(System.in);

log.log(Level.INFO, "Enter Old Pin: ");
int oldPin = in.nextInt();
log.log(Level.INFO, "Enter New Pin: ");
int newPin = in.nextInt();
AccountDAO.changePin(accountno2, oldPin, newPin);

}

private static void generatePin(int accountno2) throws SQLException {
Scanner in = new Scanner(System.in);
log.info("Enter your account number: ");
int myaccno = in.nextInt();
if (myaccno != accountno2) {
log.warn("Wrong account number.");
} else {
AccountDAO.generatePin(myaccno);
}
}

private static void checkBalance(int accountno2) throws SQLException {
Scanner in = new Scanner(System.in);
log.info("Enter your pin: ");
int mypin = in.nextInt();
AccountDAO.viewBalance(mypin, accountno2);

}

private static void showBankMenu() throws SQLException, IOException {
Scanner in = new Scanner(System.in);
do {
log.info("------------------------");
log.info(" 1. Create Account. ");
log.info(" 2. Deposit Money ");

log.info(" 3. Exit ");
log.log(Level.INFO, "Enter Choice: ");

int bankchoice = in.nextInt();
switch (bankchoice) {

case 1:
createAccount();
break;
case 2:
depositMoney();
break;

case 3:
log.info("Thankyou for Banking with us.");
showMainMenu();
break;
default:
log.info("Please make a valid choice.");

}
} while (true);
}

private static void depositMoney() throws SQLException {
Scanner in = new Scanner(System.in);

log.info("Enter your account_no: ");
int acc = in.nextInt();

log.log(Level.INFO, "Enter the amount you want to deposit: ");
int amount = in.nextInt();
boolean deposited = AccountDAO.depositMoney(acc, amount);

if (deposited) {
log.log(Level.INFO, "Amount Deposited successfully ");
} else {
log.log(Level.INFO, "Something went wrong, sorry for inconvinence! ");
}

}

/** Create account */
private static void createAccount() throws SQLException, IOException {
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
log.log(Level.INFO, "Enter First Name: (atleast three characters) ");
String fname;
while (true) {

fname = br.readLine();
if (!fname.matches("([a-zA-Z]{2,})")) {
log.log(Level.INFO, "Enter a valid user name.");
} else {
break;
}

}

log.log(Level.INFO, "Enter Last Name:(atleast three characters) ");
String lname;
while (true) {

lname = br.readLine();
if (!lname.matches("([a-zA-Z]{2,})")) {
log.log(Level.INFO, "Enter a valid user name.");
} else {
break;
}

}

log.info("Enter your Email: ");
String email;

while (true) {

email = br.readLine();
try {
if (!email.matches("[a-zA-Z][a-zA-Z0-9_.]+@(.+)$")) {

log.log(Level.INFO, "Please enter a valid email id");
} else {

if (!AccountDAO.validateUniqueEmail(email)) {
break;
} else {

log.info("This Email is already registered.");
}

}
} catch (Exception e) {
log.warn(e);
}
}
log.log(Level.INFO, "Enter Mobile Number: ");
String mobileno;
// "(0/91)?[7-9][0-9]{9}"

while (true) {

mobileno = br.readLine();

if (!mobileno.matches("[6-9][0-9]{9}")) {

log.log(Level.INFO, "Please enter a valid Mobile No");
} else {
break;
}

}

Account account = new Account(fname, lname, email, mobileno);

int accountno = random.nextInt(1000000);
account.setAccountNo(accountno);

boolean answer = AccountDAO.createNewAccount(account);
if (answer) {
log.info("Account created successfully.Take your documents! ");

AccountDAO.getYourdebitcard(account);

} else {
log.log(Level.INFO, "Something went wrong.....Please visit after some time..! \n Sorry for inconvinence! ");
}

}

}
