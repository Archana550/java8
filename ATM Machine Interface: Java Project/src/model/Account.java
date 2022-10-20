package model;

public class Account {

private int accountNo ;
private String fname;
private String lname;
private String email;

private String mobileno;

private int pin;

public Account(String fname, String lname,String email, String mobileno) {


this.fname = fname;
this.lname = lname;
this.email = email;
this.mobileno = mobileno;

}
public String getFName() {
return fname;
}
public void setFName(String name) {
this.fname = name;
}

public String getLName() {
return lname;
}
public void setLName(String name) {
this.lname = name;
}

public String getEmail() {
return email;
}
public void setEmail(String email) {
this.email = email;
}


public int getAccountNo() {
return accountNo;
}
public void setAccountNo(int accountNo) {
this.accountNo = accountNo;
}
public String getMobileno() {
return mobileno;
}
public void setMobileno(String mobileno) {
this.mobileno = mobileno;
}

public int getPin() {
return pin;
}
public void setPin(int pin) {
this.pin = pin;
}
@Override
public String toString() {
return "Your Passbook: [accountNo=" + accountNo + ", First name=" + fname + ", Last name=" + lname + ", Email=" + email
+ ", Mobile no=" + mobileno + "]";
}


}

