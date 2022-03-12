package estelle.storage;

import java.util.HashMap;
import java.util.Map;


public enum DataType {
	BOOLEAN(Boolean.TYPE, Boolean.class, true, 1), 
	INT(Integer.TYPE, Integer.class, true, Integer.SIZE), 
	LONG(Long.TYPE, Long.class, true, Long.SIZE),
	FLOAT(Float.TYPE, Float.class, true, Float.SIZE),
	DOUBLE(Double.TYPE, Double.class, true, Double.SIZE),
	DATE(java.sql.Date.class, java.sql.Date.class, true, 0), 
	TIME(java.sql.Time.class, java.sql.Time.class, true, 0),
	STRING(String.class, String.class, false, -1),
	OBJECT(String.class, String.class, false, -1);

	//
	public final Class dtClass;
	public final String dtName;
	private final boolean isFixedLength;
	public final int size;

	private static final Map<String, DataType> MAP = new HashMap<>();

	static {
		for (DataType value : values()) {
			MAP.put(value.dtName, value);
		}
	}

	DataType(Class dtType, Class dtClass, boolean isFixedLength, int size) {
		this.dtClass = dtType;
		this.dtName = dtClass.getSimpleName();
		this.isFixedLength = isFixedLength;
		this.size = size;
	}

	public static DataType of(String dtName) {
		return MAP.get(dtName);
	}
}
