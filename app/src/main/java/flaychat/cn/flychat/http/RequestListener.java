package flaychat.cn.flychat.http;

public interface  RequestListener {

/**
 * 请求前进行的操作
 */
public void onPreRequest();
/**
 * 请求成功的时候进行的操作
 */
public void onRequestSuccess(BaseResponse response);
/**
 * 请求错误的时候进行的操作
 */
public void onRequestError(int code, String msg);
/**
 * 请求失败的时候进行的操作
 */
public void onRequestFail(int code, String msg);
}
