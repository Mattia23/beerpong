package com.mirri.mirribilandia.util;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe con metodi statici di utilità
 */
public final class Utilities {

	private Utilities() { }

	public static String BEACON_ID = "";

	/**
	 * Legge una stringa da uno stream di dati.
	 * @param inputStream
	 * @return
	 * @throws IOException
     */
	public static String readStringFromStream(final InputStream inputStream) throws IOException {
		int byteRead = 1;
		byte bytes[] = new byte[1024];
		final StringBuilder stringBuilder = new StringBuilder();

		while(byteRead > 0) {
			byteRead = inputStream.read(bytes, 0, bytes.length);

			if(byteRead > 0) {
				stringBuilder.append(new String(bytes, 0, byteRead));
			}
		}
		return stringBuilder.toString();
	}

}
