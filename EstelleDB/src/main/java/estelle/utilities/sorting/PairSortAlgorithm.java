package estelle.utilities.sorting;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Eric Shi
 *
 * @param <T>
 * 
 * Sorting algorithm wrapper for data grid.
 */
public abstract class PairSortAlgorithm<T extends Comparable<? super T>> {

	public abstract List<T> sort(List<T> data, List<Integer> indexes);
	
	// Generate the original data from sorted data based on the index
	public List<T> generateSortedDataFromIndex(List<T> data, List<Integer> indexes) {
		List<T> dataTmp = new ArrayList<T>();
		for (int i: indexes) {
			dataTmp.add(data.get(i));
		}		
		return dataTmp;		
	}
}
