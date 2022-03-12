package estelle.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import estelle.config.CommonConstants;

/**
 * 
 * @author Eric Shi
 *
 * @param <T>
 * 
 * Class Grid is a base class for different types of data grid with multiple data items.
 * 
 * A data grid is a container for homogeneous data items. It can contain single data item of non-structural 
 * data (image/blob/clob...), or multiple homogeneous data items of plain data type (integer, float, string ...).
 *  
 * For data grid with multiple data items, its size is fixed: in general 2^16.
 * 
 * A data grid consists of:
 * - isSingleton: flag to indicate if the DP contains multiple data items or single item.
 * - isSortable: flag to indicate that the data items in the grid can be sorted, if it is not a singleton. 
 * - isDeleted: flag to indicate if all the data item(s) in the DP deleted.
 * 
*/
public abstract class Grid<T extends Comparable<? super T> >  {
	protected GridIdentifier gridUID;
	protected DataType dataType;
	protected boolean isSingleton;
	protected boolean isSortable;
	protected boolean isDeleted;
	
	protected List<T> dataItems;

	public Grid(List<T> dataItems, DataType dataType, boolean isSortable, boolean isSingleton) {		
		this.dataItems = dataItems;
		initialize(dataType, isSortable, isSingleton, false);
	}
	
	public Grid(DataType dataType, boolean isSortable, boolean isSingleton) {
		// Create new data items
		this.dataItems = new ArrayList<T>(estelle.config.CommonConstants.DATA_GRID_SIZE);
		initialize(dataType, isSortable, isSingleton, false);
	}
	
	private void initialize(DataType dataType, boolean isSortable, boolean isSingleton, boolean isDeleted) {
		this.dataType = dataType;
		this.isSortable = isSortable;
		this.isSingleton = isSingleton;
		this.isDeleted = false;
	}
	
	public abstract Grid<T> clone();
	
	//
	public abstract List<T> getItems(List<Integer> indexes);	
	public abstract T getItem(Integer index);
	public abstract boolean append(T item);
	public abstract boolean appendAll(List<T> items);

	// Physically the remove data item(s) from the list
	public abstract boolean remove(Integer index);
	public abstract boolean remove(List<Integer> indexes);	

	//
	public List<T> getAllItems() {
		return dataItems;
	}
	
	public int size() {
		return dataItems.size();
	}

	public boolean isEmpty() {
		return dataItems.isEmpty();
	}
	
	public boolean isFull() {
		return dataItems.size() == CommonConstants.DATA_GRID_SIZE;
	}

	public boolean contains(T e) {
		return dataItems.contains(e);
	}

	public boolean containsAll(Collection c) {
		return dataItems.containsAll(c);
	}

	public Iterator iterator() {
		return dataItems.iterator();
	}
	
	public ListIterator listIterator() {
		return dataItems.listIterator();
	}
}
