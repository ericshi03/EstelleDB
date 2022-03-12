package estelle.utilities.compressor;

import java.util.List;

public interface Compressor<T> {
	byte[] compress(byte input[]);
	byte[] uncompress(byte[] input);
}
