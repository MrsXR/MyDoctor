package cn.gem.util;

import com.google.gson.Gson;

public class CommonGson {
	
	private static Gson instance=new Gson();
	
	public static Gson getGson() {
		return instance;
	}
	private CommonGson() {
		
	}

}
