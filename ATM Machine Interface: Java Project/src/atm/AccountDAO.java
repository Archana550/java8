package atm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Objects;

import model.Account;
import model.ConnectionProvider;
import utility.GenerateDebitCard;

import java.util.Random;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class AccountDAO {

private AccountDAO() {
throw new IllegalStateException();
}

/**
* encoder.encodeToString(account.getFName().getBytes()); static Decoder decoder
* = Base64.getDecoder(); byte[] bytes = decoder.decode(encodedstring);
* Decrypted value = new String(bytes)
*/
static Encoder encoder = Base64.getEncoder();

static Decoder decoder = Base64.getDecoder();

static Random random = new Random();
static Logger log = LogManager.getLogger(AccountDAO.class.getName());

/** New Account Creation
* @throws UniqueEmail */
public static boolean createNewAccount(Account account) throws SQLException {

boolean flag = false;
Connection con = null;
PreparedStatement pstmt = null;

Statement stmt = null;

String query = "insert into customer(first_name,last_name,email, mobileno,account_no) values(?,?,?,?,?)";
try {

con = ConnectionProvider.createConnection();
pstmt = con.prepareStatement(query);

pstmt.setString(1, account.getFName());
pstmt.setString(2, account.getLName());
pstmt.setString(3, encoder.encodeToString(account.getEmail().getBytes()));
pstmt.setString(4, encoder.encodeToString(account.getMobileno().getBytes()));
pstmt.setInt(5, account.getAccountNo());

if (pstmt.executeUpdate() > 0)
flag = true;

} catch(SQLIntegrityConstraintViolationException  ex) {
log.info("Already registered mail.");
}catch (Exception e) {
e.printStackTrace();
} finally {
log.log(Level.INFO, account);
try {
if (con != null)
con.close();
} catch (Exception e2) {
e2.printStackTrace();
}
try {
if (pstmt != null)
pstmt.close();
} catch (Exception e3) {
e3.printStackTrace();
}
}

return flag;

}

/**
* Debit Card Displayed on screen and also created in a text file in Docs folder
*/
public static void getYourdebitcard(Account user) throws SQLException {

Connection con = null;
PreparedStatement pstmt2 = null;

int cvv = random.nextInt(1000);
int pin = random.nextInt(10000);
long cardNumber = Math.round(random.nextFloat() * Math.pow(10, 12));

try {
con = ConnectionProvider.createConnection();
String query2 = "insert into debitcard(account_no, cardnumber, cvv,pin) values(?,?,?,?);";
pstmt2 = con.prepareStatement(query2);
pstmt2.setLong(1, user.getAccountNo());
pstmt2.setString(2, encoder.encodeToString(Long.toString(cardNumber).getBytes())/* cardNumber */);
pstmt2.setString(3, encoder.encodeToString(Integer.toString(cvv).getBytes()) /* cvv */);
pstmt2.setString(4, encoder.encodeToString(Integer.toString(pin).getBytes()) /* pin */);
pstmt2.executeUpdate();

} catch (Exception e) {
e.printStackTrace();
} finally {
try {
if (con != null)
con.close();
} catch (Exception e2) {
e2.printStackTrace();
}
try {
if (pstmt2 != null)
pstmt2.close();
} catch (Exception e3) {
e3.printStackTrace();
}
}
String name = (user.getFName().toUpperCase()) + " " + (user.getLName()).toUpperCase();
GenerateDebitCard.debitcard(user.getEmail(), cardNumber, cvv, name);

log.log(Level.INFO, "******************************************");

log.info("Card Number : {}", cardNumber);
log.info("CVV :{} ", cvv);
log.info("Card holder's name: ");

log.info("{}\t{}", (user.getFName().toUpperCase()), (user.getLName()).toUpperCase());

log.info("*******************************************");

}

/** Money deposited to account number, directly through bank. */
public static boolean depositMoney(int acc, int deposit) throws SQLException {

int bal = 0;
boolean flag = false;
Statement stmt = null;
Connection con = null;

try {
con = ConnectionProvider.createConnection();

String query = "select balance from debitcard where account_no=" + acc + ";";

stmt = con.createStatement();

ResultSet set = stmt.executeQuery(query);

while (set.next()) {
bal = set.getInt(1);

}

bal = bal + deposit;

String query2 = "update debitcard set balance =" + bal + " where account_no=" + acc + ";";

stmt.executeUpdate(query2);

log.info("Your available balance is: ");
log.info(bal);

flag = true;
} catch (Exception e) {
e.printStackTrace();
} finally {
try {
if (con != null)
con.close();
} catch (Exception e2) {
e2.printStackTrace();
}
try {
if (stmt != null)
stmt.close();
} catch (Exception e3) {
e3.printStackTrace();
}
}
return flag;
}

/** Withdraw amount using your card and pin */
public static void withdrawMoney(int accountno2, String mypin, int withdraw) throws SQLException {

int bal = 0;

Statement stmt = null;
Connection con = null;

try {

con = ConnectionProvider.createConnection();
String query = "select balance from debitcard where account_no=" + accountno2 + " && pin=" + mypin + ";";

stmt = con.createStatement();

ResultSet set = stmt.executeQuery(query);

if (!set.next()) {

log.info("Wrong pin.");
}

else {

bal = set.getInt(1);

if (bal % 500 == 0) {

if (bal < withdraw) {
log.info("You don't have sufficient balance in your account.");
log.info("You Available Balance :{}", bal);
} else {

bal = bal - withdraw;

String query2 = "update debitcard set balance =" + bal + " where account_no=" + accountno2
+ ";";

stmt.executeUpdate(query2);

log.info("You Remaining Balance :{}", bal);

}
} else {
log.log(Level.INFO, "Please enter in multiples of 500.");
}
}
} catch (Exception e) {
e.printStackTrace();
} finally {
try {
if (con != null)
con.close();
} catch (Exception e2) {
e2.printStackTrace();
}
try {
if (stmt != null)
stmt.close();
} catch (Exception e3) {
e3.printStackTrace();
}
}

}

public static void viewBalance(int mypin, int myaccountno) throws SQLException {

int bal = 0;

Connection con = null;
Statement stmt = null;

try {

con = ConnectionProvider.createConnection();
String query = "select  balance from debitcard where pin=" + mypin + " && account_no=" + myaccountno + ";";

stmt = con.createStatement();

ResultSet set = stmt.executeQuery(query);

if (set.next()) {
bal = set.getInt(1);

log.info("Available balance:{} ", bal);
} else {
log.info("wrong pin");
}
} catch (Exception e) {
e.printStackTrace();
} finally {
try {
if (con != null)
con.close();
} catch (Exception e2) {
e2.printStackTrace();
}
try {
if (stmt != null)
stmt.close();
} catch (Exception e3) {
e3.printStackTrace();
}
}

}
/**Checks the database for email, if exists it will not show message email already registered*/
public static boolean validateUniqueEmail(String email) throws SQLException {
Connection con = null;
Statement stmt = null;

String encodedmail = encoder.encodeToString(email.getBytes());
try {
con = ConnectionProvider.createConnection();

String query = "select email from customer;";

stmt = con.createStatement();

ResultSet set = stmt.executeQuery(query);
while (set.next()) {
if (Objects.equals(set.getString(1), encodedmail)) {
return true;
}

}

} catch (Exception e) {
e.printStackTrace();
} finally {
try {
if (con != null)
con.close();
} catch (Exception e2) {
e2.printStackTrace();
}
try {
if (stmt != null)
stmt.close();
} catch (Exception e3) {
e3.printStackTrace();
}
}

return false;
}
/**A pin is generated using random class of java*/
public static void generatePin(int acc) throws SQLException {
Connection con = null;
PreparedStatement pstmt = null;

try {
con = ConnectionProvider.createConnection();

int firstpin = random.nextInt(10000);
String query = "update debitcard set pin =" + firstpin + " where account_no=?;";

pstmt = con.prepareStatement(query);

pstmt.setLong(1, acc);
pstmt.executeUpdate();
log.info("Your pin is -");
log.info(firstpin);

} catch (Exception e) {
e.printStackTrace();
} finally {
try {
if (con != null)
con.close();
} catch (Exception e2) {
e2.printStackTrace();
}
try {
if (pstmt != null)
pstmt.close();
} catch (Exception e3) {
e3.printStackTrace();
}
}

}

/** Change pin using old pin and new pin and card inserted */
public static void changePin(int accountno2, int oldPin, int newPin) throws SQLException {

Connection con = null;
PreparedStatement pstmt = null;

String query1 = "select account_no from debitcard where account_no=?;";
boolean flag = false;
int accNo = 0;
try {
con = ConnectionProvider.createConnection();
pstmt = con.prepareStatement(query1);
pstmt.setInt(1, accountno2);

ResultSet rs = pstmt.executeQuery();
if (rs.next()) {
accNo = rs.getInt(1);
flag = true;
} else {
log.log(Level.INFO, "Wrong card Number");
}
} catch (SQLException e) {

e.printStackTrace();
} finally {
try {
if (con != null)
con.close();
} catch (Exception e2) {
e2.printStackTrace();
}
try {
if (pstmt != null)
pstmt.close();
} catch (Exception e3) {
e3.printStackTrace();
}
}
if (flag) {
String query2 = "update debitcard set pin=? where pin=? && account_no=?;";
PreparedStatement pstmt2 = null;
Connection con2 = null;
try {
con2 = ConnectionProvider.createConnection();
pstmt2 = con2.prepareStatement(query2);
pstmt2.setInt(1, newPin);
pstmt2.setInt(2, oldPin);
pstmt2.setInt(3, accNo);
pstmt2.executeUpdate();
log.log(Level.INFO, "Your Pin is Changed Successfully.");
} catch (SQLException e) {

e.printStackTrace();
} finally {

try {
if (con2 != null)
con2.close();
} catch (Exception e2) {
e2.printStackTrace();
}
try {
if (pstmt2 != null)
pstmt2.close();
} catch (Exception e3) {
e3.printStackTrace();
}

}

}
}

/** Searches account number using cardnumber and cvv */
public static int findAccountNumber(String cardnumber, String cvv) {
Connection con = null;
PreparedStatement pstmt = null;

String query1 = "select account_no from debitcard where cardnumber=? && cvv=?;";
boolean flag = false;
int accNo = 0;
try {
con = ConnectionProvider.createConnection();
pstmt = con.prepareStatement(query1);
pstmt.setString(1, encoder.encodeToString(cardnumber.getBytes()));
pstmt.setString(2, encoder.encodeToString(cvv.getBytes()));

ResultSet rs = pstmt.executeQuery();
if (rs.next()) {
accNo = rs.getInt(1);
flag = true;
} else {
log.log(Level.INFO, "Invalid card");
}
} catch (SQLException e) {

e.printStackTrace();
} finally {
try {
if (con != null)
con.close();
} catch (Exception e2) {
e2.printStackTrace();
}
try {
if (pstmt != null)
pstmt.close();
} catch (Exception e3) {
e3.printStackTrace();
}

}
return accNo;

}
}
