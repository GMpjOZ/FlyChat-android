package flaychat.cn.flychat.register;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;

import flaychat.cn.flychat.R;
import flaychat.cn.flychat.util.DateUtils;
import flaychat.cn.flychat.util.TextUtils;


/**
 * 设置生日的信息，继承于抽象的注册页面
 * @author pj
 *
 */
public class StepBirthday extends RegisterStep implements OnDateChangedListener {

	private TextView mHtvConstellation;
	private TextView mHtvAge;
	private DatePicker mDpBirthday;

	private Calendar mCalendar;
	private Date mMinDate;
	private Date mMaxDate;
	private Date mSelectDate;
	private static final int MAX_AGE = 100;
	private static final int MIN_AGE = 12;

	public StepBirthday(RegisterActivity activity, View contentRootView) {
		super(activity, contentRootView);
		initData();

	}

	/**
	 * 显示日历效果
	 * @param calendar 日历
	 */
	private void flushBirthday(Calendar calendar) {
		String constellation = TextUtils.getConstellation(
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
		mSelectDate = calendar.getTime();
		mHtvConstellation.setText(constellation);
		int age = TextUtils.getAge(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		mHtvAge.setText(age + "");
	}

	/**
	 * 初始化时间，提供可以选择的时间范围
	 */
	private void initData() {
		mSelectDate = DateUtils.getDate("19900101");

		Calendar mMinCalendar = Calendar.getInstance();
		Calendar mMaxCalendar = Calendar.getInstance();

		mMinCalendar.set(Calendar.YEAR, mMinCalendar.get(Calendar.YEAR)
				- MIN_AGE);
		mMinDate = mMinCalendar.getTime();
		mMaxCalendar.set(Calendar.YEAR, mMaxCalendar.get(Calendar.YEAR)
				- MAX_AGE);
		mMaxDate = mMaxCalendar.getTime();

		mCalendar = Calendar.getInstance();
		mCalendar.setTime(mSelectDate);
		flushBirthday(mCalendar);
		mDpBirthday.init(mCalendar.get(Calendar.YEAR),
				mCalendar.get(Calendar.MONTH),
				mCalendar.get(Calendar.DAY_OF_MONTH), this);
	}

	@Override
	public void initViews() {
		mHtvConstellation = (TextView) findViewById(R.id.reg_birthday_htv_constellation);
		mHtvAge = (TextView) findViewById(R.id.reg_birthday_htv_age);
		mDpBirthday = (DatePicker) findViewById(R.id.reg_birthday_dp_birthday);
	}

    public String getData(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(mSelectDate);
        return dateString;
    }
	@Override
	public void initEvents() {

	}

	@Override
	public void doNext() {
		mOnNextActionListener.next();
	}

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public boolean isChange() {
		return false;
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		mCalendar = Calendar.getInstance();
		mCalendar.set(year, monthOfYear, dayOfMonth);
		if (mCalendar.getTime().after(mMinDate)
				|| mCalendar.getTime().before(mMaxDate)) {
			mCalendar.setTime(mSelectDate);
			mDpBirthday.init(mCalendar.get(Calendar.YEAR),
					mCalendar.get(Calendar.MONTH),
					mCalendar.get(Calendar.DAY_OF_MONTH), this);
		} else {
			flushBirthday(mCalendar);
		}
	}

}
