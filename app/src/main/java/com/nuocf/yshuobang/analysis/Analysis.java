package com.nuocf.yshuobang.analysis;


import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.nuocf.yshuobang.pool.SimpleObjectPool;
import com.nuocf.yshuobang.utils.DLog;

public class Analysis {
	private final static String DEBUG = "Analysis";

	/**
	 * 流解析
	 * 
	 * @param handler
	 * @param is
	 */
	public final static void parseFromStream(DefaultHandler handler,
			InputStream is) {
		try {
			JsonParse jsonParse = mSimpleObjectPool.acquire();
			if (jsonParse != null) {
				DLog.d(DEBUG, "reuse the parseClass: " + jsonParse);
				jsonParse.executeParseIns(handler, is);
			} else {
				jsonParse = new JsonParse();
				DLog.d(DEBUG, "create new parseClass: " + jsonParse);
				jsonParse.executeParseIns(handler, is);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 字符串解析
	 * 
	 * @param handler
	 * @param is
	 * @throws JSONException 
	 */
	public final static void parseFromString(DefaultHandler handler,
			InputStream is) throws JSONException {
		try {
			JsonParse jsonParse = mSimpleObjectPool.acquire();
			if (jsonParse != null) {
				DLog.d(DEBUG, "reuse the parseClass: " + jsonParse);
				jsonParse.executeParseString(handler, is);
			} else {
				jsonParse = new JsonParse();
				DLog.d(DEBUG, "create new parseClass: " + jsonParse);
				jsonParse.executeParseString(handler, is);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对象映射Json
	 * 
	 * @param cls
	 * @param is
	 * @return
	 */
	public final static <T> T parseGson(Class<T> cls, InputStream is) {
		T result = null;
		try {
			GsonParse<T> gsonParse = new GsonParse<T>();
			result = gsonParse.executeParse(cls, is);
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 对象数组映射JSon
	 * 
	 * @param is
	 * @return
	 */
	public final static <T> ArrayList<T> parseArrayGson(InputStream is) {
		ArrayList<T> results = null;
		try {
			GsonParse<T> gsonParse = new GsonParse<T>();
			results = gsonParse.executeJustArrayParse(is);
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;
	}

	protected final static void releaseJsonReader(JsonParse jsonReaderParse) {
		DLog.d(DEBUG, "release the parseClass: " + jsonReaderParse);
		mSimpleObjectPool.release(jsonReaderParse);
	}

	private final static SimpleObjectPool<JsonParse> mSimpleObjectPool = new SimpleObjectPool<JsonParse>(
			1);
}
