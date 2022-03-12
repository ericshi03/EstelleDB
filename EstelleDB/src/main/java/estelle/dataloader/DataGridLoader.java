package estelle.dataloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import estelle.storage.Grid;
import estelle.storage.GridDescriptor;

public class DataGridLoader implements DataLoader {	
	private static final Logger log = (Logger) LogManager.getLogger(DataGridLoader.class);
	 
	public <T extends Grid> T load(GridDescriptor discriptor) {
		String datFileName = ""; //discriptor.getFileName();

	    try {
	        long start = System.currentTimeMillis();	
	        byte[] allBytes = Files.readAllBytes(Paths.get(datFileName));	
	        long end = System.currentTimeMillis();
	        log.info("Loading data object " + datFileName + " (" + (end - start) + " ms)");
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	    
		return null;
	}

	public Grid load(String dataSetId) {
		// TODO Auto-generated method stub
		return null;
	}
}

/*

if (logger.isDebugEnabled()) {
    logger.debug("Logging in user " + user.getName() + " with birthday " + user.getBirthdayCalendar());
}
  
 */
