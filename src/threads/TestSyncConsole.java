package threads;

import java.util.concurrent.locks.ReentrantLock;

public class TestSyncConsole extends Thread{
	ReentrantLock lock;

	public TestSyncConsole(String name, ReentrantLock lock){
		super(name);
		this.lock = lock;
		
	}
	
	private void sum(){
		this.lock.lock();
		int n1; int n2;
		n1 = SynchronizedConsole.readInt(String.format("Thread %s: Number 1? ", this.getName()));
		n2 = SynchronizedConsole.readInt(String.format("Thread %s: Number 2? ", this.getName()));
		SynchronizedConsole.println(String.format("Thread %s: %s + %s = %s", this.getName(),n1, n2, n1 + n2));
		this.lock.unlock();
		
	}
	
	public void run(){
		sum();
	}
	
	public static void main(String[] args){
		ReentrantLock lock = new ReentrantLock();
		TestSyncConsole testConsoleA = new TestSyncConsole("A", lock);
		TestSyncConsole testConsoleB = new TestSyncConsole("B", lock);
		testConsoleA.start();
		testConsoleB.start();
	}
	
}
