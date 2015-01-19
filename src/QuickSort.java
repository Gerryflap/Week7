
public class QuickSort {
	public static void qsort(int[] a) {
		qsort(a, 0, a.length - 1);
	}
	public static void qsort(int[] a, int first, int last) {
		if (first < last) {
			int position = partition(a, first, last);
			Thread thread1 = new SwekThread(a, first, position - 1);
			Thread thread2 = new SwekThread(a, position + 1, last);
			thread1.start();
			thread2.start();
			try{
				thread1.join();
				thread2.join();
			} catch(Exception e) {}
			
			
		}
	}
	public static int partition(int[] a, int first, int last) {

		int mid = (first + last) / 2;
		int pivot = a[mid];
		swap(a, mid, last); // put pivot at the end of the array
		int pi = first;
		int i = first;
		while (i != last) {
			if (a[i] < pivot) {
				swap(a, pi, i);
				pi++;
			}
			i++;
		}
		swap(a, pi, last); // put pivot in its place "in the middle"
		return pi;
	}
	public static void swap(int[] a, int i, int j) {
		int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}

}

class SwekThread extends Thread{
	private int[] a;
	private int first;
	private int last;
	
	public SwekThread(int[] a, int first, int last){
		this.a = a;
		this.first = first;
		this.last = last;
	}
	
	public void run(){
		QuickSort.qsort(this.a, this.first, this.last);
	}
	
	public static void main(String[] args){
		int[] swek = new int[]{3,5,1,9,0,-10,10,5,-5,1,-4,-5};
		QuickSort.qsort(swek);	
		
		for(int swekker: swek){
			System.out.println(swekker);
		}
	}
}
