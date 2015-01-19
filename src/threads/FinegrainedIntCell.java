package threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class FinegrainedIntCell implements IntCell {
	private int value = 0;
	private ReentrantLock lock;
	private Condition doWrite;
	private Condition doRead;
	private boolean written;
	
	public FinegrainedIntCell(){		
		lock = new ReentrantLock();
		doRead = lock.newCondition();
		doWrite = lock.newCondition();
		
	}
	
	public void setValue(int valueArg) {
		lock.lock();
		while (written){
			try {
				doWrite.await();
			} catch (InterruptedException e) {}
		}
		this.value = valueArg;
		written = true;
		doRead.signal();
		lock.unlock();
	}

	public int getValue() {
		lock.lock();
		while (!written){
			try {
				doRead.await();
			} catch (InterruptedException e) {}
		}
		written = false;
		doWrite.signal();
		lock.unlock();
		return this.value;
	}
}
