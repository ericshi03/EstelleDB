package estelle.config;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * 
 * @author Eric Shi
 *
 */
public final class SystemConfig {	
	private static final String SYS_CONFIG_PROPERTY_NAME = "sysconfig.properties";

	private static final Properties PROPERTIES = loadSysConfig();
	
	//
	public static Boolean getBooleanProperty(String key, Boolean defaultValue) { 
		String value = getProperty(key, defaultValue);
		if (value == null)
			return defaultValue;
		
		if (value.equalsIgnoreCase("Y") || value.equalsIgnoreCase("YES"))
			return true;
		return false;
	}
	
	public static String getStringProperty(String key, String defaultValue) {
		String value = getProperty(key, defaultValue);
		if (value == null)
			return defaultValue;
		return value;		
	}

	public static Integer getIntegerProperty(String key, Integer defaultValue) { 
		String value = getProperty(key, defaultValue);
		if (value == null)
			return defaultValue;
		
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			return defaultValue;	        	  
		}
	}	

	// 
	private static <T> String getProperty(String key, final T defaultValue) {
        if (key == null || key.isEmpty()) {
            throw new InvalidParameterException(MessageFormat.format(CommonConstants.ERR_CONFIG_VALUEY_NOT_FOUND, key));
        }
		
        final String value = PROPERTIES.getProperty(key);
        if (value == null && defaultValue == null) {
            throw new InvalidParameterException(MessageFormat.format(CommonConstants.ERR_CONFIG_VALUEY_NOT_FOUND, key));
        }
        return value;
    }
			
	private static Properties loadSysConfig() {
		Properties properties = new Properties();
     	try (InputStream inStream = SystemConfig.class.getClassLoader().getResourceAsStream(SYS_CONFIG_PROPERTY_NAME)) {

     		if (inStream == null) {
     			throw new InvalidParameterException(MessageFormat.format(CommonConstants.ERR_CANNOT_FIND_CONFIG_FILE, SYS_CONFIG_PROPERTY_NAME));
     		}
     		properties.load(inStream);
  
     	} catch (IOException ex) {
     		ex.printStackTrace();
     	}    
     	
     	return properties;
	}
}
