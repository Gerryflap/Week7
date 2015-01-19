
public class ConcatThread extends Thread {
	private static String text = ""; // global variable
	private String toe;

	public ConcatThread(String toeArg) {
		this.toe = toeArg;
	}

	public void run() {
		synchronized(ConcatThread.text ){
			ConcatThread.text = ConcatThread.text.concat(toe);
		}
	}

	public static void main(String[] args) {
		(new ConcatThread("one;")).start();
		(new ConcatThread("two;")).start();
	}
}
