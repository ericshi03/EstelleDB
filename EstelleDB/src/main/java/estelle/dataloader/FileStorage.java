package estelle.dataloader;

import estelle.storage.Grid;

public class FileStorage {
	public Grid getDateSet(String gridId) {
		// Get the data set from cache.
		// TODO: Link to data cache mgr ...
		
		Grid dataSet = null;
		
		// Simple cache
//		if (dataSets.get(gridId) == null) {
//			// Load it from storage
//			// TODO: Using storage mgr ...
//			dataSet = dataLoader.load(gridId);
//			dataSets.put(gridId, dataSet);
//		}
		
		return dataSet;
	}
}
