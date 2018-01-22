package com.nuocf.yshuobang.analysis;


import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public final class GsonParse<R> {
	private final static String ENC = "UTF-8";

	public GsonParse() throws JsonIOException, JsonSyntaxException,
			UnsupportedEncodingException {
	}

	/**
	 * execute the json just as start JsonObject
	 * 
	 * @param r
	 * @param is
	 * @return
	 * @throws JsonIOException
	 * @throws JsonSyntaxException
	 * @throws IOException
	 */
	public R executeParse(Class<R> cls, InputStream is) throws JsonIOException,
			JsonSyntaxException, IOException {
		Gson gson = new Gson();
		R result = gson.fromJson(
				new JsonReader(new InputStreamReader(is, ENC)), cls);
		is.close();
		return result;
	}

	/**
	 * execute the json just as Array <li>
	 * like:[{\"name\":\"Michael\",\"age\":20},{\"name\":\"Mike\",\"age\":21}]
	 * 
	 * @param is
	 * @return
	 * @throws JsonIOException
	 * @throws JsonSyntaxException
	 * @throws IOException
	 */
	public ArrayList<R> executeJustArrayParse(InputStream is)
			throws JsonIOException, JsonSyntaxException, IOException {
		Type listType = new TypeToken<ArrayList<R>>() {
		}.getType();
		Gson gson = new Gson();
		ArrayList<R> results = gson.fromJson(new JsonReader(
				new InputStreamReader(is, ENC)), listType);
		is.close();
		return results;
	}
}
