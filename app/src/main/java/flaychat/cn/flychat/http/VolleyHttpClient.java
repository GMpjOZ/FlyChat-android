package flaychat.cn.flychat.http;

import android.app.Dialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

import java.util.Map;


/**
 * volley的客户端，进行请求操作的接口
 * @author pj
 *
 */
public class VolleyHttpClient {

	private static VolleyHttpClient mInstance; //客户端自身，可用于初始化
	private VolleySingleton volleySingleton; //单例，创建唯一的Volley对象
	private Dialog dialog; //一些操作的弹出框
	private Context context; //上下文

	/**
	 * 初始化Volley客户端
	 * @param context
	 * @return
	 */
	public static synchronized VolleyHttpClient getInstance(Context context){
		if(mInstance==null){
			mInstance=new VolleyHttpClient(context);
		}
		return mInstance;
	}

	/**
	 * 构造方法，初始化Volley
	 * @param context
	 */
	private VolleyHttpClient(Context context){
		this.context=context;
		volleySingleton= VolleySingleton.getInstance(context);
	}

	/**
	 * post请求
	 * @param url 请求的地址
	 * @param params 请求的参数
	 * @param loadingMsg 是否进行弹出对话框提示
	 * @param listener 事件监听器
	 */
	public void post(String url,
			Map<String ,String> params,int loadingMsg,
			final RequestListener listener){
		request(Request.Method.POST, url, params, loadingMsg, listener);
	}

	/**
	 * get请求
	 * @param url 请求的地址
	 * @param loadingMsg 是否进行弹出对话框提示
	 * @param listener 事件监听器
	 */
	public void get(String url,
			int loadingMsg,
			final RequestListener listener){
		request(Request.Method.GET, url, null, loadingMsg, listener);
	}

	/**
	 * 发送的请求
	 * @param method
	 * @param url
	 * @param params
	 * @param loadingMsg
	 * @param listener
	 */
	private void request(int method,String url,
			Map<String ,String> params,int loadingMsg,
			final RequestListener listener){
			//判断监听的时间的是否为空，如果为空调用请求前进行的操作
		if(listener==null)
			listener.onPreRequest();
		//showLoading(loadingMsg);
		BaseRequest request=new BaseRequest(method, url, params,
				new Listener<BaseResponse>(){
			/**
			 * 请求正确进行的操作
			 * @param response
			 */
				public void onResponse(BaseResponse response) {
						
					//dismissLoading();
						
					if(listener!=null){
						if(response.getMsg().equals("success"))
							listener.onRequestSuccess(response);
						else listener.onRequestFail(response.getStatus(), response.getMsg());
						}
					}
			}, new ErrorListener() {
	
				/**
				 * 请求错误进行的操作，将错误信息在监听器中进行处理
				 */
				@Override
				public void onErrorResponse(VolleyError error) {
						
					//dismissLoading();
						
					String errMsg= VolleyErrorHelper.getMessage(context, error);
					if(errMsg==null)
						if(listener!=null)
							listener.onRequestError(error.networkResponse.statusCode, errMsg);
						
					}
			});

		volleySingleton.addToRequestQuene(request);
	}
	
	private void showLoading(int msg){
		if(msg>0){
			dialog.show();
		}
	}
	private void dismissLoading(){
		if(dialog.isShowing())
			dialog.dismiss();
	}
}
