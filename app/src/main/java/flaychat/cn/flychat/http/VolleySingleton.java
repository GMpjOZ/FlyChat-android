package flaychat.cn.flychat.http;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

	private static VolleySingleton mInstance;
	private RequestQueue mRequestQueue;
	public static synchronized VolleySingleton getInstance(Context context){
		if(mInstance==null){
			mInstance=new VolleySingleton(context.getApplicationContext());
		}
		return mInstance;
	}
	
	private VolleySingleton(Context context){
		mRequestQueue= Volley.newRequestQueue(context);
	}
	
	public RequestQueue getRequestQueue(){
		return this.mRequestQueue;
	}
	
	public <T> void addToRequestQuene(Request<T> req){
		getRequestQueue().add(req);
	}
	
	public void cancleRequest(Object tag){
		getRequestQueue().cancelAll(tag);
	}
	
}
