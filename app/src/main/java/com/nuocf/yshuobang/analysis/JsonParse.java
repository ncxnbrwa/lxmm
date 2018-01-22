package com.nuocf.yshuobang.analysis;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;



public final class JsonParse {
	private final static String ENC = "UTF-8";

	public JsonParse() {
	}

	public void executeParseIns(DefaultHandler handler, InputStream is)
			throws UnsupportedEncodingException, IOException {
		InputStreamReader inr = null;
		JsonReader reader = null ;
		try {
			inr = new InputStreamReader(is, ENC);
			reader = new JsonReader(inr);
			handler.parseJson(reader);
		} finally {
			inr.close();
			reader.close();
			Analysis.releaseJsonReader(this);
		}
	}

	public void executeParseString(DefaultHandler handler, InputStream is)
			throws IOException, JSONException {
		try {
			handler.parseJson(anlyInputStream(is));
		} finally {
			Analysis.releaseJsonReader(this);
		}
	}

	private String anlyInputStream(InputStream is) throws IOException {
		ByteArrayOutputStream stream = null;
		stream =  new ByteArrayOutputStream(1024);
		byte[] bytes = new byte[4096];
		int length = 0;
		while ((length = is.read(bytes)) != -1) {
			stream.write(bytes, 0, length);
		}
		is.close();
		return stream.toString(ENC);
	}

}
