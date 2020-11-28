import java.util.List;
import java.util.ArrayList;

/* Basic tester to see if merge sort can handle ints */
public class SorterTester {

	public static void main(String[] args) {
		sortRandomRange(100);
	}

	public static void sortRandomRange(int n) {
		Sorter<Integer> sorter = new Sorter<Integer>();
		List<Integer> list = new ArrayList<Integer>();

		for(int i = 0; i < n; i++) 
			if(i % 2 == 0)
				list.add((int) (Math.random() * 100));
			else
				list.add((int) (Math.random() * -100));


		System.out.print("[ ");
		for(int i = 0; i < list.size(); i++)
			System.out.print(list.get(i) + ", ");		
		System.out.println("]\n");


		sorter.mergeSort(list);

		System.out.print("[ ");
		for(int i = 0; i < list.size(); i++)
			System.out.print(list.get(i) + ", ");
		System.out.println("]\n");
	}
}