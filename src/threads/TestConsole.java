package threads;

public class TestConsole extends Thread{

	public TestConsole(String name){
		super(name);
	}
	
	private void sum(){
		int n1; int n2;
		n1 = Console.readInt(String.format("Thread %s: Number 1? ", this.getName()));
		n2 = Console.readInt(String.format("Thread %s: Number 2? ", this.getName()));
		Console.println(String.format("Thread %s: %s + %s = %s", this.getName(),n1, n2, n1 + n2));
	}
	
	public void run(){
		sum();
	}
	
	public static void main(String[] args){
		TestConsole testConsoleA = new TestConsole("A");
		TestConsole testConsoleB = new TestConsole("B");
		testConsoleA.start();
		testConsoleB.start();
	}
	
}
