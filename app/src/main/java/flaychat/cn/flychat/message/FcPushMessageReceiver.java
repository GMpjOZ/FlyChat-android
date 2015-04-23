package flaychat.cn.flychat.message;

import android.content.Context;
import android.util.Log;

import com.baidu.frontia.api.FrontiaPushMessageReceiver;

import java.util.List;

public class FcPushMessageReceiver extends FrontiaPushMessageReceiver {

	private String TAG="JzPushMessageReceiver";
	@Override
	public void onBind(Context context, int errorCode, String appid,  
	 	 	 	String userId, String channelId, String requestId) {
		// TODO Auto-generated method stub
		Log.e(TAG, "errorCode"+errorCode);
		Log.e(TAG, "appid"+appid);
		Log.e(TAG, "userId"+userId);
        Log.e(TAG, "channelId"+channelId);

	}

	@Override
	public void onDelTags(Context arg0, int arg1, List<String> arg2,
			List<String> arg3, String arg4) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onListTags(Context arg0, int arg1, List<String> arg2,
			String arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(Context context, String message, String customContentString) {
		// TODO Auto-generated method stub
		String messageString = "透传消息 message=" + message + " customContentString=" 
 	 	 	 	+ customContentString; 
 	 	Log.e(TAG, messageString);

	}

	@Override
	public void onNotificationClicked(Context arg0, String arg1, String arg2,
			String arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSetTags(Context arg0, int arg1, List<String> arg2,
			List<String> arg3, String arg4) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnbind(Context arg0, int arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

}
