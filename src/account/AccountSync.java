package account;

public class AccountSync {
	public static void main(String[] args) throws InterruptedException{
		Account account = new Account();
		account.transaction(0);
		MyThread thread1 = new MyThread(10.5, 200, account);
		MyThread thread2 = new MyThread(-10.5, 200, account);
		thread1.start();
		thread2.start();
		//new Thread(thread1).start();
		
		thread1.join();
		thread2.join();
		System.out.println(account.getBalance());
	}
}
