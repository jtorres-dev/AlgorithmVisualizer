import java.util.List;
import java.util.ArrayList;

public class Sorter <T> {
	
	public Sorter() {}

	public static <T extends Comparable<T>> void mergeSort(List<T> list) {
		mergeSortHelper(list, 0, list.size());
	}

	private static <T extends Comparable<T>> void mergeSortHelper(List<T> list, int first, int last) {
		
		if(last <= 1) return;
		
		List<T> left, right;
		int mid;

		left = new ArrayList<T>();
		right = new ArrayList<T>();

		mid = (first + last) / 2;

		for(int i = 0; i < mid; i++)
			left.add(list.get(i));

		for(int i = mid; i < last; i++)
			right.add(list.get(i));
		
		mergeSort(left);
		mergeSort(right);

		placeBack(list, left, right);
	}

	private static <T extends Comparable<T>> void placeBack(List<T> list, List<T> left, List<T> right) {
		int l, r, m;
		l = r = m = 0;

		while(left.size() != l && right.size() != r) {
			
			if(left.get(l).compareTo(right.get(r)) < 0)
				list.set(m++, left.get(l++));
			else
				list.set(m++, right.get(r++));
		}

		while(left.size() != l)
			list.set(m++, left.get(l++));

		while(right.size() != r)
			list.set(m++, right.get(r++));

	}
}