package utility;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;

import java.io.FileReader;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import atm.Menu;

public class ReadCard {
static Logger log = LogManager.getLogger(ReadCard.class.getName());

public static String[] readcard() throws IOException, NumberFormatException, SQLException {

log.info("-------------------");
log.info("Insert your card.");
log.info("-------------------");
List<String> listOfStrings = new ArrayList<String>();

try {
Scanner sc = new Scanner(new FileReader(chooseTextFile()));
log.info("--------Processing your card-----------");
/** String to store the file words. */
String str;

/** checking end of file */

while (sc.hasNext()) {
str = sc.next();

/** adding each string to arraylist */
listOfStrings.add(str);
}
} catch (Exception e) {

log.info("Card not inserted properly.");
Menu.showMainMenu();

}

/** convert any arraylist to array */

String[] array = listOfStrings.toArray(new String[0]);
String cardnumber = null;
String cvv = null;
try {
cardnumber = array[1];
cvv = array[3];
} catch (Exception e) {
log.info("---Invalid card----");
Menu.showMainMenu();
}
String[] credentials = { cardnumber, cvv };

return credentials;

}

private static File chooseTextFile() {

FileDialog fd = new FileDialog((Frame) null, "Choose a file", FileDialog.LOAD);
fd.setDirectory("C:\\Users\\KARCHANA\\eclipse-workspace\\ATMInterface\\Docs");
fd.setFile("*.txt");
fd.setVisible(true);
File[] file = fd.getFiles();

if (file == null)
log.info("You cancelled the choice.");
else {
log.info("Welcome," + file[0].getName() + " !");
return file[0];
}
return null;

}

}

