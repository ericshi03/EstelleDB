package estelle.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author Eric Shi
 *
 * @param <T>
 * 
 * SimpleGrid contains multiple data items which should not be sorted e.g. data with 
 * data type of boolean, byte, char, ... 
 */
public class SimpleGrid<T extends Comparable<? super T>>  extends Grid<T> {

	// Create a data grid from a data list
	public SimpleGrid(List<T> dataItems, DataType dataType, boolean isSortable, boolean isSingleton) {
		super(dataItems, dataType, false, isSingleton);
	}

	// Create a new data grid
	public SimpleGrid(DataType dataType, boolean isSortable, boolean isSingleton) {
		super(dataType, false, isSingleton);
	}
	
	//
	@Override
	public List<T> getItems(List<Integer> indexes) {
		return indexes.stream()
                .map(i -> dataItems.get(i))
                .collect(Collectors.toList());
	}

	@Override
	public T getItem(Integer index) {
		return dataItems.get(index);
	}

	@Override
	public boolean append(T item) {
		if (dataItems.size() == estelle.config.CommonConstants.DATA_GRID_SIZE) {
			return false;
		}
		return dataItems.add(item);
	}

	@Override
	public boolean appendAll(List<T> items) {
		if (dataItems.size() + items.size() > estelle.config.CommonConstants.DATA_GRID_SIZE) {
			return false;
		}		
		return dataItems.addAll(items);
	}

	@Override
	public boolean remove(Integer index) {
		return dataItems.remove(index); 
	}

	@Override
	public boolean remove(List<Integer> indexes) {
		boolean r = true;
		for (Integer i: indexes) {
			r = r && dataItems.remove(i);
		}
		return r;
	}

	@Override
	public Grid<T> clone() {
		return new SimpleGrid<T>(new ArrayList<T>(dataItems), dataType, isSortable, isSingleton);
	}	
}
