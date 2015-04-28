package flaychat.cn.flychat.register;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewFlipper;

import java.util.HashMap;
import java.util.Map;

import flaychat.cn.flychat.API;
import flaychat.cn.flychat.BaseActivity;
import flaychat.cn.flychat.R;
import flaychat.cn.flychat.http.BaseResponse;
import flaychat.cn.flychat.http.RequestListener;
import flaychat.cn.flychat.sys.FlayChatApplication;


/**
 * 注册页面activity
 * @author pj
 *
 */
public class RegisterActivity extends BaseActivity implements OnClickListener,
        RegisterStep.onNextActionListener {

//	private HeaderLayout mHeaderLayout;//界面标题
	private ViewFlipper mVfFlipper;
	private Button mBtnPrevious;
	private Button mBtnNext;

	private Dialog mBackDialog;
	
	private RegisterStep mCurrentStep;
	private StepPhone mStepPhone;
	private StepVerify mStepVerify;
	private StepSetPassword mStepSetPassword;
	private StepBaseInfo mStepBaseInfo;
	private StepBirthday mStepBirthday;
	private StepPhoto mStepPhoto;

	private int mCurrentStepIndex = 1;//当前步骤

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
        Log.w("1","");
		initViews();
        Log.w("2","");
		mCurrentStep = initStep();
		initEvents();
        Log.w("3","");
//		initBackDialog();
	}

	@Override
	protected void onDestroy() {
//		PhotoUtils.deleteImageFile();
		super.onDestroy();
	}

	/**
	 * 初始化页面展示
	 */

	protected void initViews() {
//		mHeaderLayout = (HeaderLayout) findViewById(R.id.reg_header);
//		mHeaderLayout.init(HeaderStyle.TITLE_RIGHT_TEXT);
		mVfFlipper = (ViewFlipper) findViewById(R.id.reg_vf_viewflipper);
		mVfFlipper.setDisplayedChild(0);
		mBtnPrevious = (Button) findViewById(R.id.reg_btn_previous);
		mBtnNext = (Button) findViewById(R.id.reg_btn_next);
	}

	/**
	 * 初始化事件
	 */

	protected void initEvents() {
		mCurrentStep.setOnNextActionListener(this);
		mBtnPrevious.setOnClickListener(this);
		mBtnNext.setOnClickListener(this);
	}

	/**
	 * 重写返回键，实现返回上一步，如果为第一步弹出对话框提示
	 */
	@Override
	public void onBackPressed() {
		if (mCurrentStepIndex <= 1) {
			mBackDialog.show();
		} else {
			doPrevious();
		}
	}

	/**
	 * 事件处理，上一步和下一步的事件处理
	 */
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.reg_btn_previous:
			if (mCurrentStepIndex <= 1) {
//				mBackDialog.show();
			} else {
				doPrevious();
			}
			break;

		case R.id.reg_btn_next:
			if (mCurrentStepIndex < 6) {
				doNext();
			} else {
				if (mCurrentStep.validate()) {
					mCurrentStep.doNext();
				}

                Map<String,String> map=new HashMap<String,String>();
                map.put("phone",mStepPhone.getPhoneNumber());
                map.put("password",mStepSetPassword.getPassword());
                map.put("name",mStepBaseInfo.getName());
                map.put("sex",mStepBaseInfo.getSex()+"");
                map.put("birthday",mStepBirthday.getData());
                Log.w("mHttpClient",mHttpClient.toString());
                mHttpClient.post(API.Register,map,0,new RequestListener() {
                    @Override
                    public void onPreRequest() {
                        Log.w("重发","重发");
                    }

                    @Override
                    public void onRequestSuccess(BaseResponse response) {
                        Log.w("成功","成功");
                        AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext())
                                .setMessage("注册成功");
                        AlertDialog dialog=builder.create();
                        dialog.show();
                    }

                    @Override
                    public void onRequestError(int code, String msg) {
                        Log.w("错误","错误");
                    }

                    @Override
                    public void onRequestFail(int code, String msg) {
                        Log.w("失败","失败");
                    }
                });
			}
			break;
		}
	}

//	/**
//	 * 设置用户头像
//	 * 从子模块向主activity传递数据的回调函数
//	 * @param requestCode 是以便确认返回的数据是从哪个Activity返回的
//	 * @param resultCode 是由子Activity通过其setResult()方法返回
//	 * @param data 一个Intent对象，带有返回的数据
//	 */

	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//		super.onActivityResult(requestCode, resultCode, data);
//		switch (requestCode) {
//		/**
//		 * 如果从相册返回
//		 */
//		case PhotoUtils.INTENT_REQUEST_CODE_ALBUM:
//			if (data == null) {
//				return;
//			}
//			/**
//			 * 如果正常返回，判断是否有数据
//			 */
//			if (resultCode == RESULT_OK) {
//				if (data.getData() == null) {
//					return;
//				}
//				if (!FileUtils.isSdcardExist()) {
//					showCustomToast("SD卡不可用,请检查");
//					return;
//				}
//
//				/**
//				 * 获取图片信息,并设置用户头像
//				 */
//				Uri uri = data.getData();
//				String[] proj = { MediaStore.Images.Media.DATA };
//				Cursor cursor = managedQuery(uri, proj, null, null, null);
//
//				if (cursor != null) {
//					int column_index = cursor
//							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//					//一般通过判断cursor.moveToFirst()的值为true或false来确定查询结果是否为空
//					if (cursor.getCount() > 0 && cursor.moveToFirst()) {
//						String path = cursor.getString(column_index);
//						Bitmap bitmap = BitmapFactory.decodeFile(path);
//						if (PhotoUtils.bitmapIsLarge(bitmap)) {
//							PhotoUtils.cropPhoto(this, this, path);
//						} else {
//							mStepPhoto.setUserPhoto(bitmap);
//						}
//					}
//				}
//			}
//			break;
//
//			/**
//			 * 如果从照相返回，则将照相照片设置为用户头像
//			 */
//		case PhotoUtils.INTENT_REQUEST_CODE_CAMERA:
//			if (resultCode == RESULT_OK) {
//				String path = mStepPhoto.getTakePicturePath();
//				Bitmap bitmap = BitmapFactory.decodeFile(path);
//				if (PhotoUtils.bitmapIsLarge(bitmap)) {
//					PhotoUtils.cropPhoto(this, this, path);
//				} else {
//					mStepPhoto.setUserPhoto(bitmap);
//				}
//			}
//			break;
//
//			/**
//			 * 如果从裁剪照片返回，获取照片的位置，设置为用户头像
//			 */
//		case PhotoUtils.INTENT_REQUEST_CODE_CROP:
//			if (resultCode == RESULT_OK) {
//				String path = data.getStringExtra("path");
//				if (path != null) {
//					Bitmap bitmap = BitmapFactory.decodeFile(path);
//					if (bitmap != null) {
//						mStepPhoto.setUserPhoto(bitmap);
//					}
//				}
//			}
//			break;
//		}
	}


/**
	 * 下一步的操作
	 */

	public void next() {
		mCurrentStepIndex++;
		mCurrentStep = initStep();
		mCurrentStep.setOnNextActionListener(this);
		//设置屏幕动画
//		mVfFlipper.setInAnimation(this, R.anim.push_left_in);
//		mVfFlipper.setOutAnimation(this, R.anim.push_left_out);
		mVfFlipper.showNext();
	}

	/**
	 * 根据步骤初始化当前页面，仅设置标题和左右按钮
	 * @return
	 */
	private RegisterStep initStep() {
		switch (mCurrentStepIndex) {
		case 1:
			if (mStepPhone == null) {
				mStepPhone = new StepPhone(this, mVfFlipper.getChildAt(0));

			}
//			mHeaderLayout.setTitleRightText("注册新账号", null, "1/6");
			mBtnPrevious.setText("返    回");
			mBtnNext.setText("下一步");
			return mStepPhone;

		case 2:
            Log.w("mStepPhone2",mStepPhone.getPhoneNumber()+"");
			if (mStepVerify == null) {
				mStepVerify = new StepVerify(this, mVfFlipper.getChildAt(1));
                Log.w("mStepVerify",mStepVerify+"");
			}
//			mHeaderLayout.setTitleRightText("填写验证码", null, "2/6");
			mBtnPrevious.setText("上一步");
			mBtnNext.setText("下一步");
			return mStepVerify;

		case 3:
			if (mStepSetPassword == null) {
				mStepSetPassword = new StepSetPassword(this,
						mVfFlipper.getChildAt(2));

			}
//			mHeaderLayout.setTitleRightText("设置密码", null, "3/6");
			mBtnPrevious.setText("上一步");
			mBtnNext.setText("下一步");
			return mStepSetPassword;

		case 4:
            Log.w("mStepSetPassword",mStepSetPassword.getPassword()+"");
			if (mStepBaseInfo == null) {
				mStepBaseInfo = new StepBaseInfo(this, mVfFlipper.getChildAt(3));

			}
//			mHeaderLayout.setTitleRightText("填写基本资料", null, "4/6");
			mBtnPrevious.setText("上一步");
			mBtnNext.setText("下一步");
			return mStepBaseInfo;

		case 5:
            Log.w("mStepBaseInfo",mStepBaseInfo.getName()+"");
            Log.w("mStepBaseInfo",mStepBaseInfo.getSex()+"");
			if (mStepBirthday == null) {
				mStepBirthday = new StepBirthday(this, mVfFlipper.getChildAt(4));

			}
//			mHeaderLayout.setTitleRightText("您的生日", null, "5/6");
			mBtnPrevious.setText("上一步");
			mBtnNext.setText("下一步");
			return mStepBirthday;

		case 6:
            Log.w("mStepBirthday",mStepBirthday.getData()+"");
			if (mStepPhoto == null) {
				mStepPhoto = new StepPhoto(this, mVfFlipper.getChildAt(5));
                Log.w("mStepPhoto",mStepPhoto+"");
			}
//			mHeaderLayout.setTitleRightText("设置头像", null, "6/6");
			mBtnPrevious.setText("上一步");
			mBtnNext.setText("注    册");
			return mStepPhoto;
		}
		return null;
	}

	/**
	 * 返回上一步
	 */
	private void doPrevious() {
		mCurrentStepIndex--;
		mCurrentStep = initStep();
		mCurrentStep.setOnNextActionListener(this);
//		mVfFlipper.setInAnimation(this, R.anim.push_right_in);
//		mVfFlipper.setOutAnimation(this, R.anim.push_right_out);
		mVfFlipper.showPrevious();
	}

	/**
	 * 
	 */
	private void doNext() {
		if (mCurrentStep.validate()) {
			if (mCurrentStep.isChange()) {
				mCurrentStep.doNext();
			} else {
				next();
			}
		}
	}

	/**
	 * 返回键的对话框
	 */
//	private void initBackDialog() {
////		mBackDialog = Dialog.getDialog(RegisterActivity.this, "提示",
////				"确认要放弃注册么?", "确认", new DialogInterface.OnClickListener() {
////
////					@Override
////					public void onClick(DialogInterface dialog, int which) {
////						dialog.dismiss();
////						finish();
////					}
////				}, "取消", new DialogInterface.OnClickListener() {
////
////					@Override
////					public void onClick(DialogInterface dialog, int which) {
////						dialog.cancel();
////					}
////				});
////		mBackDialog.setButton1Background(R.drawable.btn_default_popsubmit);
//
//	}


//	protected void putAsyncTask(AsyncTask<Void, Void, Boolean> asyncTask) {
////		super.putAsyncTask(asyncTask);
//	}


//	protected void showCustomToast(String text) {
//		super.showCustomToast(text);
//	}


//	protected void showLoadingDialog(String text) {
////		super.showLoadingDialog(text);
//	}


//	protected void dismissLoadingDialog() {
////		super.dismissLoadingDialog();
//	}

//	protected int getScreenWidth() {
////		return mScreenWidth;
//        return 0;
//	}

//	protected Application getBaseApplication() {
//		return getApplication();
//
//	}

	protected String getPhoneNumber() {
		if (mStepPhone != null) {
			return mStepPhone.getPhoneNumber();
		}
		return "";
	}

}
