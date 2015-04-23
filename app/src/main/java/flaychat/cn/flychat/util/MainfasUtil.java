package flaychat.cn.flychat.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

public class MainfasUtil {

	public static String getValue(Context context,String metaKey){
		Bundle metaDate=null;
		String apiKey=null;
		
		if(context==null||metaKey==null){
			return null;
		}
		try {
			ApplicationInfo ai=context.getPackageManager()
					.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			if(ai!=null){
				metaDate=ai.metaData;
			}
			if(metaDate!=null){
				apiKey=metaDate.getString(metaKey);
			}
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return apiKey;
	}
}
