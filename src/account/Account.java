package account;

public class Account {
	protected double balance = 0.0;

	public synchronized void transaction(double amount) throws InterruptedException{
		while(balance + amount < -1000){
			wait();
		}
		balance = balance + amount;
	}
	public double getBalance() {
		return balance;

	}
}
