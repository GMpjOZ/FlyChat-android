package flaychat.cn.flychat.http;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import flaychat.cn.flychat.R;


//import android.support.v7.app.ActionBarActivity;


/**
 * 请求错误类，进行错误类型的判别
 * @author pj
 *
 */
public class VolleyErrorHelper {

	public static String getMessage(Context context,VolleyError error){
		if(error instanceof TimeoutError)
			return context.getResources().getString(R.string.server_error);
		else if(isServerProblem(error))
			return handleServer(context, error);
		else if(isNetWorkProblem(error))
			return context.getResources().getString(R.string.no_internet);
		
		return context.getResources().getString(R.string.network_error);
	}

	private static String handleServer(Context context, VolleyError error) {
		NetworkResponse response=error.networkResponse;
		
		if(response!=null){
			switch(response.statusCode){
			case 404:
			case 422:
			case 401:
				return context.getResources().getString(R.string.resourse_error);
				
			default:
				return context.getResources().getString(R.string.server_error);
			}
		}
		return context.getResources().getString(R.string.network_error);
	}

	private static boolean isNetWorkProblem(VolleyError error) {
		// TODO Auto-generated method stub
		return (error instanceof NetworkError)
				||(error instanceof NoConnectionError);
	}

	private static boolean isServerProblem(VolleyError error) {
		// TODO Auto-generated method stub
		return (error instanceof ServerError)
				||(error instanceof AuthFailureError);
	}
}
