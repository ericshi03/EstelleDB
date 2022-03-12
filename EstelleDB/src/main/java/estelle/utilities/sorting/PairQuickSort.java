package estelle.utilities.sorting;

import java.util.List;


public class PairQuickSort<T extends Comparable<? super T>> extends PairSortAlgorithm<T> {
	
	// Since data may be partially sorted, the indexes cannot be restored from data
	public List<T> sort(List<T> data, List<Integer> indexes) {
		assert data.size() == indexes.size();
		
		quickSort(data, indexes, 0, data.size() - 1);
		
		// Generate the sorted data based on the index
		return generateSortedDataFromIndex(data, indexes);
	}

	// Quick sort implementation
	private void quickSort(List<T> data, List<Integer> indexes, int start, int end) {
		int partition = partition(data, indexes, start, end);
		if (partition - 1 > start) {
			quickSort(data, indexes, start, partition - 1);
		}
		if (partition + 1 < end) {
			quickSort(data, indexes, partition + 1, end);
		}
	}

	private int partition(List<T> data, List<Integer> indexes, int start, int end) {
		// Only the index are actually sorted, since the data part might be large. 
		T pivot = data.get(indexes.get(end));
		int iPivot = indexes.get(end);

		for (int i = start; i < end; i++) {
			if (data.get(indexes.get(i)).compareTo(pivot) > 0) {
				// data part
//				T temp = data.get(start);
//				data.set(start, data.get(i));
//				data.set(i, temp);

				// index part
				int iTemp = indexes.get(start);
				indexes.set(start, indexes.get(i));
				indexes.set(i, iTemp);

				start++;
			}
		}

//		T temp = data.get(start);
//		data.set(start, pivot);
//		data.set(end, temp);

		int iTemp = indexes.get(start);
		indexes.set(start, iPivot);
		indexes.set(end, iTemp);

		return start;
	}

}
