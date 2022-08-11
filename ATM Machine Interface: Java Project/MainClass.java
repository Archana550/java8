import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AtmOperationInterf op = new AtmOperationlmpl();
			int atmnumber=12345;
			int atmpin =123;
			
			Scanner in = new Scanner(System.in);
			System.out.println("Welcome to ATM Machine !!!");
			System.out.println("Enter ATM number: ");
			int atmNumber = in.nextInt();
			System.out.println("Enter you secret number: ");
			int pin = in.nextInt();
			if((atmnumber == atmNumber)&& (atmpin==pin)) {
				//System.out.println("Validation Done");
				while(true) {
					System.out.println("1.View Available Balance\n 2. Withdraw Amount\n 3.Deposit Amount\n 4. View MiniStatemnet\n 5. Exit ");
					
					System.out.println("Enter Choice: ");
					int ch = in.nextInt();
					
					if (ch == 1){
						op.viewBalance();
					}else if(ch==2) {
						System.out.println("Enter amount to withdraw: ");
						double withdrawAmount = in.nextDouble();
						op.withdrawAmount(withdrawAmount);
					}else if(ch == 3) {
						System.out.println("Enter Amount to deposit : ");
						double depositAmount = in.nextDouble();
						op.depositAmount(depositAmount);
					}
					else if(ch==4) {
						op.viewMiniStatement();
					}else if(ch==5) {
						System.out.println("Collect your ATM Card. Thankyou for visiting.");
						System.exit(0);
					}
				}
				
			}else {
				System.out.println("Wrong Pin.");
				System.exit(0);
			}
			in.close();
	}
	

}
