package estelle.storage;

public class GridIdentifier {
	// Simple id
	private String schemaId;
	private String tableId;
	private String columnId;
	private String gridId;

	// File name of the grid
	public String getGridUID() {		
		return schemaId + "_" + tableId + "_" + columnId + "_" + gridId;
	}
}
