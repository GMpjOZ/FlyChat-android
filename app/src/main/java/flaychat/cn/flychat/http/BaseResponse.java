package flaychat.cn.flychat.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BaseResponse {

	private int status; //系统提供的请求错误编码
	private String msg; //人为编写的错误信息
	private String data; //请求的信息
	private Gson gson=new GsonBuilder().setDateFormat("yyyy-mm-dd HH:MMM:SS").create();
	
	/**
	 * 将json解析出对象
	 * @param clz 泛型，解析出的类型
	 * @return
	 */
	public <T> T getObject(Class<T> clz){
		if(TextUtils.isEmpty(data)){
			return null;
		}
		else return gson.fromJson(data, clz);
	}
	
	/**
	 * 将json解析出list
	 * @param clz 泛型，解析出的类型
	 * @return
	 */
	public <T> List<T> getList(Class<T> clz){
		if(TextUtils.isEmpty(data)){
			return null;
		}
		List<T> list=new ArrayList<T>();
		Type listType=type(List.class,clz);
		list=gson.fromJson(data, listType);
		return list;
	}
	
	static ParameterizedType type(final Class raw,final  Type... args){
		return new ParameterizedType() {
			
			@Override
			public Type getRawType() {
				// TODO Auto-generated method stub
				return raw;
			}
			
			@Override
			public Type getOwnerType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Type[] getActualTypeArguments() {
				// TODO Auto-generated method stub
				return args;
			}
		};
	}


	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
}
