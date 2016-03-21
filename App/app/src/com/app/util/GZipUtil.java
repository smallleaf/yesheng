package com.app.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipUtil {
	private static final int BUFFER = 1024;

	public static byte[] compress(byte[] input) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(input);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		compress(bais, baos);
		byte[] output = baos.toByteArray();
		baos.flush();
		baos.close();
		bais.close();
		return output;
	}

	public static byte[] decompress(byte[] data) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		decompress(bais, baos);
		data = baos.toByteArray();
		baos.flush();
		baos.close();
		bais.close();
		return data;
	}

	public static void compress(InputStream is, OutputStream os)
			throws Exception {
		GZIPOutputStream gos = new GZIPOutputStream(os);
		int count;
		byte data[] = new byte[BUFFER];
		while ((count = is.read(data, 0, BUFFER)) != -1) {
			gos.write(data, 0, count);
		}
		gos.finish();
		gos.flush();
		gos.close();
	}

	public static void decompress(InputStream is, OutputStream os)
			throws Exception {
		GZIPInputStream gis = new GZIPInputStream(is);
		int count;
		byte data[] = new byte[BUFFER];
		while ((count = gis.read(data, 0, BUFFER)) != -1) {
			os.write(data, 0, count);
		}
		gis.close();
	}
}
