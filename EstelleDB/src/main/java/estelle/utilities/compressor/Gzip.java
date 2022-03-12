package estelle.utilities.compressor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 
 * @author Eric Shi
 *
 * @param <T>
 * 
 * Using Gzip to compress data grid
 */
public class Gzip<T> implements Compressor<T> {
	public byte[] compress(byte srcBytes[]) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (GZIPOutputStream gzip = new GZIPOutputStream(out);) {
			gzip.write(srcBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	public byte[] uncompress(byte[] bytes) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (ByteArrayInputStream in = new ByteArrayInputStream(bytes);
				GZIPInputStream ungzip = new GZIPInputStream(in);) {

			byte[] buffer = new byte[2048];
			int n;
			while ((n = ungzip.read(buffer)) >= 0) {
				out.write(buffer, 0, n);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}
}