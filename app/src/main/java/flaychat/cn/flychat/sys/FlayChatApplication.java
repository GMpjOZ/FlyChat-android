package flaychat.cn.flychat.sys;

import android.app.Application;

import com.baidu.frontia.FrontiaApplication;
import com.baidu.mapapi.SDKInitializer;

public class FlayChatApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		FrontiaApplication.initFrontiaApplication(this);
        SDKInitializer.initialize(getApplicationContext());
	}
}
