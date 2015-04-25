package flaychat.cn.flychat.chat;

import java.util.ArrayList;
import java.util.List;

import flaychat.cn.flychat.R;

/*TODO还没写完
import com.google.gson.Gson;
import com.way.adapter.MessageAdapter;
import com.way.app.PushApplication;
import com.way.bean.MessageItem;
import com.way.bean.User;
import com.way.common.util.HomeWatcher;
import com.way.common.util.L;
import com.way.common.util.SharePreferenceUtil;
import com.way.db.MessageDB;
import com.way.db.RecentDB;*/
import flaychat.cn.flychat.chat.swipeback.SwipeBackActivity;
import flaychat.cn.flychat.chat.adapter.FaceAdapter;
import flaychat.cn.flychat.chat.adapter.FacePageAdeapter;
import flaychat.cn.flychat.chat.view.CirclePageIndicator;
import flaychat.cn.flychat.chat.view.JazzyViewPager;
import flaychat.cn.flychat.chat.view.JazzyViewPager.TransitionEffect;
import flaychat.cn.flychat.chat.xlistview.MsgListView;
import flaychat.cn.flychat.chat.xlistview.MsgListView.IXListViewListener;

import android.os.Bundle;
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
	public static final int NEW_MESSAGE = 0x001;// �յ���Ϣ
	private TransitionEffect mEffects[] = { TransitionEffect.Standard,
			TransitionEffect.Tablet, TransitionEffect.CubeIn,
			TransitionEffect.CubeOut, TransitionEffect.FlipVertical,
			TransitionEffect.FlipHorizontal, TransitionEffect.Stack,
			TransitionEffect.ZoomIn, TransitionEffect.ZoomOut,
			TransitionEffect.RotateUp, TransitionEffect.RotateDown,
			TransitionEffect.Accordion, };// ���鷭ҳЧ��
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
	
	;
	private MsgListView mMsgListView;
	/**
	 * TODOprivate MessageAdapter adapter
	 * private SharePreferenceUtil mSpUtil;
	 * private User mFromUser;
	 */
	
	
	private TextView mTitle, mTitleLeftBtn, mTitleRightBtn;
	/**
	 * TODO
	 * private HomeWatcher mHomeWatcher;
	private Gson mGson;
	 */
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_main);
		initView();
		initFacePage();
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
		gv.setSelector(new ColorDrawable(Color.TRANSPARENT));// ����GridViewĬ�ϵ��Ч��
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
				if (arg2 == PushApplication.NUM) {// ɾ����λ��
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
					// ע�͵Ĳ��֣���EditText����ʾ�ַ�
					// String ori = msgEt.getText().toString();
					// int index = msgEt.getSelectionStart();
					// StringBuilder stringBuilder = new StringBuilder(ori);
					// stringBuilder.insert(index, keys.get(count));
					// msgEt.setText(stringBuilder.toString());
					// msgEt.setSelection(index + keys.get(count).length());

					// �����ⲿ�֣���EditText����ʾ����
					Bitmap bitmap = BitmapFactory.decodeResource(
							getResources(), (Integer) PushApplication
									.getInstance().getFaceMap().values()
									.toArray()[count]);
					if (bitmap != null) {
						int rawHeigh = bitmap.getHeight();
						int rawWidth = bitmap.getHeight();
						int newHeight = 40;
						int newWidth = 40;
						// ������������
						float heightScale = ((float) newHeight) / rawHeigh;
						float widthScale = ((float) newWidth) / rawWidth;
						// �½�������
						Matrix matrix = new Matrix();
						matrix.postScale(heightScale, widthScale);
						// ����ͼƬ����ת�Ƕ�
						// matrix.postRotate(-30);
						// ����ͼƬ����б
						// matrix.postSkew(0.1f, 0.1f);
						// ��ͼƬ��Сѹ��
						// ѹ����ͼƬ�Ŀ�͸��Լ�kB��С���仯
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
		// ����ListView���ر�������뷨
		mMsgListView.setOnTouchListener(this);
		mMsgListView.setPullLoadEnable(false);
		mMsgListView.setXListViewListener(this);
		/*TODO
		mMsgListView.setAdapter(adapter);
		mMsgListView.setSelection(adapter.getCount() - 1);*/
		sendBtn = (Button) findViewById(R.id.send_btn);
		faceBtn = (ImageButton) findViewById(R.id.face_btn);
		msgEt = (EditText) findViewById(R.id.msg_et);
		faceLinearLayout = (LinearLayout) findViewById(R.id.face_ll);
		faceViewPager = (JazzyViewPager) findViewById(R.id.face_pager);
		msgEt.setOnTouchListener(this);
		mTitle = (TextView) findViewById(R.id.ivTitleName);
		/*TODO
		mTitle.setText(mFromUser.getNick());*/
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
	
	// ��ֹ��pageview�ҹ���
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
		
		/**
		 * �������ûд
		 */
		@Override
		public void onClick(View v){
			
		}
		
		@Override
		public void onLoadMore() {
			// TODO Auto-generated method stub

		}
		
		/**
		 * �������ûд
		 */
		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub
			
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_chat, menu);
		return true;
	}

}
