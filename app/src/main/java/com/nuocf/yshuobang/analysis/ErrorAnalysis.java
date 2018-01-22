package com.nuocf.yshuobang.analysis;

import org.json.JSONException;
import org.json.JSONObject;

public class ErrorAnalysis extends DefaultHandler{
	private String state ;
	private String message ;

	@Override
	public void parseJson(String json) throws JSONException {
		JSONObject object = new JSONObject(json);
		state = object.getString("state");
		message = object.getString("message");
	}
	
	public String getCode(){
		return state ;
	}

	public String getMessage(){
		return message;
	}
}
