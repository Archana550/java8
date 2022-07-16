
class Account {
	public String name;
	protected String email;
	private String password;
	
	//getters and setters
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String pass) {
		this.password = pass;
	}
}

public class AccessModifiers {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Account account1 = new Account();
		account1.name = "Apna College";
		account1.email = "apnacollege@gmail.com";
		//account1.password ="asdfg";//can't access private
		account1.setPassword("abcd");
		System.out.println(account1.getPassword());

	}

}
