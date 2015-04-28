package flaychat.cn.flychat.chat.bean;

public class MessageItem {
    /**
     * 消息类型为文本
     *
     */
    public static final int MESSAGE_TYPE_TEXT = 1;
    /**
     * 消息类型为图像
     *
     */
    public static final int MESSAGE_TYPE_IMG = 2;
    /**
     * 消息类型为文件
     *
     */
    public static final int MESSAGE_TYPE_FILE = 3;

    private int msgType;//消息类型
    private String name;// 消息来自
    private long time;//消息日期
    private String message;
    private int headImg;

    private boolean isComMeg = true;//是否收到消息

    private int isNew;

    public MessageItem() {
        // TODO Auto-generated constructor stub
    }

    /**
     *
     * @param msgType 消息类型
     * @param name 消息来源
     * @param date 消息日期
     * @param message 消息内容
     * @param headImg 头像
     * @param isComMeg 是否收到消息
     * @param isNew
     */
    public MessageItem(int msgType, String name, long date, String message,
                       int headImg, boolean isComMeg, int isNew) {
        super();
        this.msgType = msgType;
        this.name = name;
        this.time = date;
        this.message = message;
        this.headImg = headImg;
        this.isComMeg = isComMeg;
        this.isNew = isNew;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return time;
    }

    public void setDate(long date) {
        this.time = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getHeadImg() {
        return headImg;
    }

    public void setHeadImg(int headImg) {
        this.headImg = headImg;
    }

    public boolean isComMeg() {
        return isComMeg;
    }

    public void setComMeg(boolean isComMeg) {
        this.isComMeg = isComMeg;
    }

    public static int getMessageTypeText() {
        return MESSAGE_TYPE_TEXT;
    }

    public static int getMessageTypeImg() {
        return MESSAGE_TYPE_IMG;
    }

    public static int getMessageTypeFile() {
        return MESSAGE_TYPE_FILE;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

}
