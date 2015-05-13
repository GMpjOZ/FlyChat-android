package flaychat.cn.flychat.sys;

import android.app.Application;

import com.baidu.frontia.FrontiaApplication;
import com.baidu.mapapi.SDKInitializer;

import flaychat.cn.flychat.chat.util.SharePreferenceUtil;
import flaychat.cn.flychat.model.User;

public class FlayChatApplication extends Application {
    private static FlayChatApplication mApplication;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		FrontiaApplication.initFrontiaApplication(this);
        SDKInitializer.initialize(getApplicationContext());
	}
}
