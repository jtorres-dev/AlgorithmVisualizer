import java.util.List;
import java.util.ArrayList;

/* Basic tester to see if merge sort can handle ints */
public class SorterTester {

	public static void main(String[] args) {
		sortRandomRange(100, 10000);
	}

	public static void sortRandomRange(int n, int maxValue) {
		Sorter<Integer> sorter = new Sorter<Integer>();
		List<Integer> list = new ArrayList<Integer>();

		for(int i = 0; i < n; i++) 
			if(i % 2 == 0)
				list.add((int) (Math.random() * maxValue));
			else
				list.add((int) (Math.random() * -maxValue));

		System.out.println(list + "\n");

		sorter.mergeSort(list);

		System.out.println(list);
	}

}