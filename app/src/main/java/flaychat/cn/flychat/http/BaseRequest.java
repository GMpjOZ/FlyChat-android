package flaychat.cn.flychat.http;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 基本的请求服务，进行请求的一些操作
 * @author pj
 *
 */
public class BaseRequest extends Request<BaseResponse> {

	private Listener<BaseResponse> mLister;
	private Map<String,String> mParams;
	
	/**
	 * 构造方法，构造出请求所需的内容
	 * @param method 请求的方式，post、get
	 * @param url 请求的地址
	 * @param params 请求的参数
	 * @param listener 请求成功的事件监听器，可以进行一些操作
 	 * @param Errorlistener 请求出错的事件监听器，进行一些操作
	 */
	public BaseRequest(int method, String url,Map<String,String> params, Listener listener	,ErrorListener Errorlistener) {
		super(method, url, Errorlistener);
		// TODO Auto-generated constructor stub
		mLister=listener;
		this.mParams=params;
	}

	private Gson mGson=new Gson();
	@Override

	protected Response<BaseResponse> parseNetworkResponse(
			NetworkResponse response) {
		// TODO Auto-generated method stub
		
		try {
			String jsonString=new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			
			BaseResponse baseResponse=parseJson(jsonString);
			return Response.success(baseResponse,
                    HttpHeaderParser.parseCacheHeaders(response));
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

	@Override
	protected void deliverResponse(BaseResponse response) {
		// TODO Auto-generated method stub
		mLister.onResponse(response);
		
	}

	
	
	@Override
	protected Map<String, String> getParams() {
		// TODO Auto-generated method stub
		return mParams;
	}

	/**
	 * 解析字符串
	 * @param json json对象的字符串
	 * @return
	 */
	private BaseResponse parseJson(String json){
		int status =0;
		String msg=null;
		String data=null;
		
		try {
			JSONObject jsonObject=new JSONObject(json);	
			status=Integer.parseInt(jsonObject.getString("status"));
			msg=jsonObject.getString("msg");
			data=jsonObject.getString("data");
			Log.e("heheda", status+msg+data);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BaseResponse response=new BaseResponse();
		response.setStatus(status);
		response.setMsg(msg);
		response.setData(data);
		return response;
				
	}
}
