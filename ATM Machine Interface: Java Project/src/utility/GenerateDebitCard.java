package utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import atm.Menu;

public class GenerateDebitCard {

static Logger log = LogManager.getLogger(GenerateDebitCard.class.getName());
public static void debitcard(String email,long cardnumber, int cvv, String name) {

String filename= email.substring(0,email.indexOf("@"));
try{
File fileObj = new File("Docs\\"+filename+".txt");
if(fileObj.createNewFile())
        {
        FileWriter writerObj = new FileWriter("Docs\\"+filename+".txt");
           
       
            writerObj.write("CardNumber " + cardnumber + "\n");
            writerObj.write("cvv " + cvv+ "\n");
            writerObj.write("Name " + name + "\n");
            writerObj.close();
            log.info("File "+fileObj.getName() +" has been created");
        }else {
        log.info("File already exists");
        }

}catch(Exception e) {
e.printStackTrace();
}



}

}

