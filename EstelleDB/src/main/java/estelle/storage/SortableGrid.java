package estelle.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import estelle.config.CommonConstants;
import estelle.utilities.sorting.PairSortAlgorithm;

/**
 * 
 * @author Eric Shi
 *
 * @param <T>
 * 
 * A sortable data grid (DG) consists of one or more data items (or data object after interpreted).
 * If a data grid is sorted, just like a cluster index, it will increase searching, and also 
 * will increase the compression.
 *  
 * A sortable data grid contins multiple data items. 
 * - A generic list of data items with data tyoe: String, Integer, ...
 *
 * There are two types of data grid:
 * - Simple data grid: Data items are not sorted and hence. It can be added/deleted/updated directly
 *   and a full scan will be required to find data 	 	
 * - Sortable data grid: Data items are sorted and thus, can be searched using binary approaches.
 *   When a data grid is sortable, followings are required
 *   	an inverted index: See below. 
 * 		a reversed index: See below.
 * 
 * ------------------------------------------------------
 * Description of data grid
 *  
 * Initially: 
 * 		d(0)=v0, i(0)=0, r(0)=0
 * 		...
 * 		d(n)=vn, i()=0, r(0)=0
 * 
 * After data is sorted (D): say d(0) moved to 5th position: 
 * 		D(5)=v0, i(0)=5, r(5)=0,
 * 
 * Data will be always accessed by original position using "inverted" index i:
 * 			d(k) = D(i(k))
 *  
 *   e.g. to get element from new index k, i.e. d(k), return D(i(k)). 
 * 		d(0) = D(i(0)) = D(5) = v0 
 * 
 * When searching a value v of data grid (sorted), original position k is required to access values at the same 
 * positions in other DPs using "reversed" index:
 * 		k = r(D.indexOf(v))
 * 
 *   e.g. to get original position of an element, i.e. d.indexOf(v), 
 * 		D.indexOf(v0)=5, r(D.indexOf(v0)) = r(5) = 0
 * 
 */
public class SortableGrid<T extends Comparable<? super T>> extends Grid<T> {
	// Inverted index. To access dataItems in the original positions
	private List<Integer> invIndexes;
	// Reversed index. To get the original positions from new positions.
	private List<Integer> revIndexes;
	// Specify if the data grid is valid, ie. the indexes are created (when it should be sorted)
	private boolean isValid = false;
	// Flag alwaysValidate will make the data sorted whenever dataItems changed (add/removed...)
	// with potential performance penalty.
	private boolean alwaysValidate = false;
	// Paired sorting algorithm
	private PairSortAlgorithm<T> pairSortAlgorithm = null;

	// Create a data grid from existing data and index
	public SortableGrid(List<T> dataItems, 
			List<Integer> invIndexes, 
			List<Integer> revIndexes,
			DataType dataType, boolean isSortable, boolean isSingleton,
			PairSortAlgorithm<T> sortAlgorithm, boolean alwaysValidate) {
		super(dataItems, dataType, false, isSingleton);
			
		this.invIndexes = invIndexes;
		this.revIndexes = revIndexes;	
		this.alwaysValidate = alwaysValidate;
		this.pairSortAlgorithm = sortAlgorithm;
	}

	// Create a data grid with existing data but rebuild indexes
	public SortableGrid(List<T> dataItems, DataType dataType, boolean isSortable, boolean isSingleton,
			PairSortAlgorithm<T> sortAlgorithm, boolean alwaysValidate) {
		super(dataItems, dataType, false, isSingleton);
		
		initialize(sortAlgorithm, alwaysValidate);
	}

	// Create a new data grid
	public SortableGrid(DataType dataType, boolean isSortable, boolean isSingleton,
			PairSortAlgorithm<T> sortAlgorithm, boolean alwaysValidate) {
		super(dataType, false, isSingleton);
		
		initialize(sortAlgorithm, alwaysValidate);
	}

	public void initialize(PairSortAlgorithm<T> sortAlgorithm, boolean alwaysValidate) {
		this.pairSortAlgorithm = sortAlgorithm;
		this.alwaysValidate = alwaysValidate;

		// ArrayList is used for fast accessing, since the list may be sorted
		invIndexes = new ArrayList<Integer>(CommonConstants.DATA_GRID_SIZE);
		revIndexes = new ArrayList<Integer>(CommonConstants.DATA_GRID_SIZE);
	}

	public void clear() {
		isValid = true;
		invIndexes.clear();
		revIndexes.clear();
		dataItems.clear();
	}

	public int indexOf(T e) {
		// Note. This is the last occurrence in the sorted list!
		int i = dataItems.indexOf(e);
		if (i == -1) {
			return i;
		}
		return revIndexes.get(i);
	}
	
	public void validate() {
		assert dataItems.size() == invIndexes.size() && dataItems.size() == revIndexes.size();

		if (!isValid) {
			rebuildIndexes();
			isValid = true;
		}
	}

	public boolean isValid() {
		return isValid;
	}
	
	private void rebuildIndexes() {
		dataItems = pairSortAlgorithm.sort(dataItems, invIndexes);

		// revIndexes.clear();
		for (int i : invIndexes) {
			revIndexes.set(invIndexes.get(i), i);
		}
	}

	@Override
	public List<T> getItems(List<Integer> indexes) {
		return indexes.stream()
                .map(i -> dataItems.get(invIndexes.get(i)))
                .collect(Collectors.toList());
	}

	@Override
	public T getItem(Integer index) {
		return dataItems.get(invIndexes.get(index));
	}
	
	@Override
	public SortableGrid<T> clone() {
		return new SortableGrid<T>(new ArrayList<T>(dataItems), 
				new ArrayList<Integer>(invIndexes), 
				new ArrayList<Integer>(revIndexes),
				dataType, isSortable, isSingleton,
				pairSortAlgorithm, alwaysValidate);	
	}

	@Override
	public boolean append(T item) {
		isValid = false;
		
		// Data items are added at the end
		int s = dataItems.size(); // One more element		
		boolean r = dataItems.add(item) && invIndexes.add(s) && revIndexes.add(s);

		// Rebuild indexes
		if (alwaysValidate) {
			validate();
		}
		return r;
	}

	@Override
	public boolean appendAll(List<T> items) {
		// Make sure not overflow
		if (dataItems.size() + items.size() > CommonConstants.DATA_GRID_SIZE)
			return false;
		
		isValid = false;
		boolean r = true;
		int s = invIndexes.size();
		for (int i = 0; i < items.size(); i++) {
			r = r  && dataItems.add(items.get(i)) && invIndexes.add(i + s) && revIndexes.add(i + s);
		}
		
		// Rebuild inverted index
		if (alwaysValidate) {
			validate();
		}
		return r;
	}
	
	@Override
	public boolean remove(Integer index) {
		assert index >= 0 && index < CommonConstants.DATA_GRID_SIZE;
		// TODO: Remove data item in the grid
		// ...
		return false;
	}

	@Override
	public boolean remove(List<Integer> indexes) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
