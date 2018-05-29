package com.mirri.mirribilandia.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe con metodi statici di utilità.
 */
public final class Utilities {

	private Utilities() { }


	public static final String MY_PREFS_NAME = "MyPrefsFile";
	public static final String RESTART = "restart";

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

	public static boolean hasPermissions(Context context, String... permissions) {
		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
			for (String permission : permissions) {
				if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
					return false;
				}
			}
		}
		return true;
	}

}
