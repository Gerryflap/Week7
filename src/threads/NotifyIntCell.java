package threads;

public class NotifyIntCell implements IntCell {
	private int value = 0;
	private boolean written;
	
	public NotifyIntCell(){
		
	}

	public synchronized void setValue(int val){
		while (written){
			try{
				wait();
			} catch(InterruptedException e){}
		}
		this.value = val;
		notifyAll();
		written = true;
	}


	public synchronized int getValue() {
		while (!written){
			try{
				wait();
			} catch(InterruptedException e){}
		}
		this.written = false;
		notifyAll();
		return this.value;
	}
	

}
