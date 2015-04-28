package flaychat.cn.flychat.chat.bean;

import java.io.Serializable;

import android.content.Context;
import android.util.Log;

import com.google.gson.annotations.Expose;
//import com.way.app.PushApplication;
import flaychat.cn.flychat.chat.util.SharePreferenceUtil;

public class Message implements Serializable{
	/**
	 * expose 先注释掉了，以后再加
	 */
	private static final long serialVersionUID = 1L;
	@Expose
	private String user_id;
	@Expose
	private String channel_id;
	@Expose
	private String nick;
	@Expose
	private int head_id;
	@Expose
	private long time_samp;
	@Expose
	private String message;
	@Expose
	private String tag;

	public Message(long time_samp, String message, String tag) {
		super();
//		SharePreferenceUtil util = PushApplication.getInstance().getSpUtil();
		this.user_id = "1111";
		this.channel_id = "2222";
		this.nick = "test";
		this.head_id = 1;
		this.time_samp = time_samp;
		this.message = message;
		this.tag = tag;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getHead_id() {
		return head_id;
	}

	public void setHead_id(int head_id) {
		this.head_id = head_id;
	}

	public long getTime_samp() {
		return time_samp;
	}

	public void setTime_samp(long time_samp) {
		this.time_samp = time_samp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "Message [user_id=" + user_id + ", channel_id=" + channel_id
				+ ", nick=" + nick + ", head_id=" + head_id + ", time_samp="
				+ time_samp + ", message=" + message + ", tag=" + tag + "]";
	}

}
