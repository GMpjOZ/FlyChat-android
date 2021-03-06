package flaychat.cn.flychat.chat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import flaychat.cn.flychat.R;

//import com.way.app.PushApplication;
import flaychat.cn.flychat.chat.bean.MessageItem;
import flaychat.cn.flychat.chat.bean.RecentItem;

import flaychat.cn.flychat.chat.util.L;
import flaychat.cn.flychat.chat.swipeback.SwipeBackActivity;
import flaychat.cn.flychat.chat.adapter.FaceAdapter;
import flaychat.cn.flychat.chat.adapter.FacePageAdeapter;
import flaychat.cn.flychat.chat.adapter.MessageAdapter;
import flaychat.cn.flychat.chat.util.SendMsgAsyncTask;
import flaychat.cn.flychat.chat.view.CirclePageIndicator;
import flaychat.cn.flychat.chat.view.JazzyViewPager;
import flaychat.cn.flychat.chat.view.JazzyViewPager.TransitionEffect;
import flaychat.cn.flychat.chat.xlistview.MsgListView;
import flaychat.cn.flychat.chat.xlistview.MsgListView.IXListViewListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * TODO ChatActivity还没写完
 * @author zaoerck
 *
 */

public class ChatActivity extends SwipeBackActivity implements
        OnTouchListener, OnClickListener, IXListViewListener{
    public static final int NEW_MESSAGE = 0x001;//收到消息
    private TransitionEffect mEffects[] = { TransitionEffect.Standard,
            TransitionEffect.Tablet, TransitionEffect.CubeIn,
            TransitionEffect.CubeOut, TransitionEffect.FlipVertical,
            TransitionEffect.FlipHorizontal, TransitionEffect.Stack,
            TransitionEffect.ZoomIn, TransitionEffect.ZoomOut,
            TransitionEffect.RotateUp, TransitionEffect.RotateDown,
            TransitionEffect.Accordion, };// 表情翻页效果
    /**
     * TODO
     * private PushApplication mApplication;
     */
    private static int MsgPagerNum;
    /**
     * TODO
     * private MessageDB mMsgDB;
     private RecentDB mRecentDB;
     */

    private int currentPage = 0;
    private boolean isFaceShow = false;
    private Button sendBtn;
    private ImageButton faceBtn;
    private EditText msgEt;
    private LinearLayout faceLinearLayout;
    private JazzyViewPager faceViewPager;
    private WindowManager.LayoutParams params;
    private InputMethodManager imm;
    private List<String> keys;
    private MsgListView mMsgListView;
    /**
     * TODO
     * private SharePreferenceUtil mSpUtil;
     * private User mFromUser;
     */

    private MessageAdapter adapter;
    private TextView mTitle, mTitleLeftBtn, mTitleRightBtn;
    /**
     * TODO
     * private HomeWatcher mHomeWatcher;
     private Gson mGson;
     */


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == NEW_MESSAGE) {
                // String message = (String) msg.obj;
                flaychat.cn.flychat.chat.bean.Message msgItem = (flaychat.cn.flychat.chat.bean.Message) msg.obj;
                String userId = msgItem.getUser_id();
                if (!userId.equals("1111"))// 如果不是当前正在聊天对象的消息，不处理
                    return;

                int headId = msgItem.getHead_id();
				/*
				 * try { headId = Integer
				 * .parseInt(JsonUtil.getFromUserHead(message)); } catch
				 * (Exception e) { L.e("head is not integer  " + e); }
				 */
                // TODO Auto-generated method stub
                MessageItem item = new MessageItem(
                        MessageItem.MESSAGE_TYPE_TEXT, "test",
                        System.currentTimeMillis(), msgItem.getMessage(),
                        headId, true, 0);
                adapter.upDateMsg(item);
//				mMsgDB.saveMsg(msgItem.getUser_id(), item);
                RecentItem recentItem = new RecentItem(userId, headId,
                        msgItem.getNick(), msgItem.getMessage(), 0,
                        System.currentTimeMillis());
//				mRecentDB.saveRecent(recentItem);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_main);
        initData();
        initView();
        initFacePage();
    }

    private void initData() {
//		mFromUser = (User) getIntent().getSerializableExtra("user");
//		if (mFromUser == null) {// 如果为空，直接关闭
//			finish();
//		}
//		mApplication = PushApplication.getInstance();
//		mSpUtil = mApplication.getSpUtil();
//		mGson = mApplication.getGson();
//		mMsgDB = mApplication.getMessageDB();
//		mRecentDB = mApplication.getRecentDB();
//		Set<String> keySet = PushApplication.getInstance().getFaceMap()
//				.keySet();
//		keys = new ArrayList<String>();
//		keys.addAll(keySet);
        MsgPagerNum = 0;
        adapter = new MessageAdapter(this, initMsgData());
    }

    /**
     * 加载消息历史，从数据库中读出
     */
    private List<MessageItem> initMsgData() {
        List<MessageItem> list = new LinkedList<MessageItem>();
        List<MessageItem> msgList = new ArrayList<MessageItem>();// 消息对象数组
        if (list.size() > 0) {
            for (MessageItem entity : list) {
                if (entity.getName().equals("")) {
                    entity.setName("test");
                }
                if (entity.getHeadImg() < 0) {
                    entity.setHeadImg(1);
                }
                msgList.add(entity);
            }
        }
        return msgList;

    }

    private void initFacePage() {
        // TODO Auto-generated method stub
        List<View> lv = new ArrayList<View>();
        /**
         * TODO
         * for (int i = 0; i < PushApplication.NUM_PAGE; ++i)
         lv.add(getGridView(i));
         */

        FacePageAdeapter adapter = new FacePageAdeapter(lv, faceViewPager);
        faceViewPager.setAdapter(adapter);
        faceViewPager.setCurrentItem(currentPage);
        /**
         * TODO
         * faceViewPager.setTransitionEffect(mEffects[mSpUtil.getFaceEffect()]);
         */

        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(faceViewPager);

        adapter.notifyDataSetChanged();
        faceLinearLayout.setVisibility(View.GONE);
        indicator.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                currentPage = arg0;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // do nothing
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // do nothing
            }
        });

    }

    private GridView getGridView(int i) {
        // TODO Auto-generated method stub
        GridView gv = new GridView(this);
        gv.setNumColumns(7);
        gv.setSelector(new ColorDrawable(Color.TRANSPARENT));// 屏蔽GridView默认点击效果
        gv.setBackgroundColor(Color.TRANSPARENT);
        gv.setCacheColorHint(Color.TRANSPARENT);
        gv.setHorizontalSpacing(1);
        gv.setVerticalSpacing(1);
        gv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        gv.setGravity(Gravity.CENTER);

        gv.setAdapter(new FaceAdapter(this, i));
        gv.setOnTouchListener(forbidenScroll());
        gv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
				/*TODO
				if (arg2 == PushApplication.NUM) {// 删除键的位置
					int selection = msgEt.getSelectionStart();
					String text = msgEt.getText().toString();
					if (selection > 0) {
						String text2 = text.substring(selection - 1);
						if ("]".equals(text2)) {
							int start = text.lastIndexOf("[");
							int end = selection;
							msgEt.getText().delete(start, end);
							return;
						}
						msgEt.getText().delete(selection - 1, selection);
					}
				} else {
					int count = currentPage * PushApplication.NUM + arg2;
					// ע注释的部分在EditText中显示字符串
					// String ori = msgEt.getText().toString();
					// int index = msgEt.getSelectionStart();
					// StringBuilder stringBuilder = new StringBuilder(ori);
					// stringBuilder.insert(index, keys.get(count));
					// msgEt.setText(stringBuilder.toString());
					// msgEt.setSelection(index + keys.get(count).length());

					// 下面这部分在EditText中显示表情
					Bitmap bitmap = BitmapFactory.decodeResource(
							getResources(), (Integer) PushApplication
									.getInstance().getFaceMap().values()
									.toArray()[count]);
					if (bitmap != null) {
						int rawHeigh = bitmap.getHeight();
						int rawWidth = bitmap.getHeight();
						int newHeight = 40;
						int newWidth = 40;
						// 新建立矩阵
						Matrix matrix = new Matrix();
						matrix.postScale(heightScale, widthScale);
						// 设置图片的旋转角度
						// matrix.postRotate(-30);
						// 设置图片的倾斜
						// matrix.postSkew(0.1f, 0.1f);
						// 将图片大小压缩
						// 压缩后图片的宽和高以及kB大小均会变化
						Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0,
								rawWidth, rawHeigh, matrix, true);
						ImageSpan imageSpan = new ImageSpan(ChatActivity.this,
								newBitmap);
						String emojiStr = keys.get(count);
						SpannableString spannableString = new SpannableString(
								emojiStr);
						spannableString.setSpan(imageSpan,
								emojiStr.indexOf('['),
								emojiStr.indexOf(']') + 1,
								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						msgEt.append(spannableString);
					} else {
						String ori = msgEt.getText().toString();
						int index = msgEt.getSelectionStart();
						StringBuilder stringBuilder = new StringBuilder(ori);
						stringBuilder.insert(index, keys.get(count));
						msgEt.setText(stringBuilder.toString());
						msgEt.setSelection(index + keys.get(count).length());
					}
				}*/
            }
        });
        return gv;
    }

    private void initView() {
        // TODO Auto-generated method stub
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        params = getWindow().getAttributes();

        mMsgListView = (MsgListView) findViewById(R.id.msg_listView);
        // 触摸ListView隐藏表情和输入法
        mMsgListView.setOnTouchListener(this);
        mMsgListView.setPullLoadEnable(false);
        mMsgListView.setXListViewListener(this);
        mMsgListView.setAdapter(adapter);
        mMsgListView.setSelection(adapter.getCount() - 1);
        sendBtn = (Button) findViewById(R.id.send_btn);
        faceBtn = (ImageButton) findViewById(R.id.face_btn);
        msgEt = (EditText) findViewById(R.id.msg_et);
        faceLinearLayout = (LinearLayout) findViewById(R.id.face_ll);
        faceViewPager = (JazzyViewPager) findViewById(R.id.face_pager);
        msgEt.setOnTouchListener(this);
        mTitle = (TextView) findViewById(R.id.ivTitleName);
        mTitle.setText("test");
        mTitleLeftBtn = (TextView) findViewById(R.id.ivTitleBtnLeft);
        mTitleLeftBtn.setVisibility(View.VISIBLE);
        // mTitleRightBtn = (TextView) findViewById(R.id.ivTitleBtnRigh);
        mTitleLeftBtn.setOnClickListener(this);
        // mTitleRightBtn.setOnClickListener(this);
        msgEt.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (params.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                            || isFaceShow) {
                        faceLinearLayout.setVisibility(View.GONE);
                        isFaceShow = false;
                        // imm.showSoftInput(msgEt, 0);
                        return true;
                    }
                }
                return false;
            }
        });
        msgEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (s.length() > 0) {
                    sendBtn.setEnabled(true);
                } else {
                    sendBtn.setEnabled(false);
                }
            }
        });
        faceBtn.setOnClickListener(this);
        sendBtn.setOnClickListener(this);
    }

    // 防止乱pageview乱滚动
    private OnTouchListener forbidenScroll() {
        return new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true;
                }
                return false;
            }
        };
    }

    public void onMessage(flaychat.cn.flychat.chat.bean.Message message) {
        Message handlerMsg = handler.obtainMessage(NEW_MESSAGE);
        handlerMsg.obj = message;
        handler.sendMessage(handlerMsg);
    }

    public void onBind(String method, int errorCode, String content) {
        // TODO Auto-generated method stub

    }

    /**
     * 还没写完
     */
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.face_btn:
                if (!isFaceShow) {
                    imm.hideSoftInputFromWindow(msgEt.getWindowToken(), 0);
                    try {
                        Thread.sleep(80);// 解决此时会黑一下屏幕的问题
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    faceLinearLayout.setVisibility(View.VISIBLE);
                    isFaceShow = true;
                } else {
                    faceLinearLayout.setVisibility(View.GONE);
                    isFaceShow = false;
                }
                break;

            case R.id.send_btn:// 发送消息
                String msg = msgEt.getText().toString();

                MessageItem item = new MessageItem(MessageItem.MESSAGE_TYPE_TEXT,
                        "test", System.currentTimeMillis(), msg,
                        1, false, 1);
                adapter.upDateMsg(item);


                // if (adapter.getCount() - 10 > 10) {
                // L.i("begin to remove...");
                // adapter.removeHeadMsg();
                // MsgPagerNum--;
                // }
                mMsgListView.setSelection(adapter.getCount() - 1);
//                    mMsgDB.saveMsg(mFromUser.getUserId(), item);
                msgEt.setText("");
                flaychat.cn.flychat.chat.bean.Message msgItem = new flaychat.cn.flychat.chat.bean.Message(
                        System.currentTimeMillis(), msg, "");
                new SendMsgAsyncTask(msg, "1111")
                        .send();

//                    RecentItem recentItem = new RecentItem(mFromUser.getUserId(),
//                            mFromUser.getHeadIcon(), mFromUser.getNick(), msg, 0,
//                            System.currentTimeMillis());
//                    mRecentDB.saveRecent(recentItem);
                break;
            //点击返回键返回
            case R.id.ivTitleBtnLeft:
                finish();
                break;
                /*
                case R.id.ivTitleBtnRigh:
                    break;*/
            default:
                break;
        }
    }

    @Override
    public void onLoadMore() {
        // TODO Auto-generated method stub

    }

    /**
     * 还没写完
     */
    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        MsgPagerNum++;
        List<MessageItem> msgList = initMsgData();
        int position = adapter.getCount();
        adapter.setMessageList(msgList);
        mMsgListView.stopRefresh();
        mMsgListView.setSelection(adapter.getCount() - position - 1);
        L.i("MsgPagerNum = " + MsgPagerNum + ", adapter.getCount() = "
                + adapter.getCount());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.msg_listView:
                imm.hideSoftInputFromWindow(msgEt.getWindowToken(), 0);
                faceLinearLayout.setVisibility(View.GONE);
                isFaceShow = false;
                break;
            case R.id.msg_et:
                imm.showSoftInput(msgEt, 0);
                faceLinearLayout.setVisibility(View.GONE);
                isFaceShow = false;
                break;

            default:
                break;
        }
        return false;
    }
}
