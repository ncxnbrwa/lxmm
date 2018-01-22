/**
 * 
 */
package com.nuocf.yshuobang.myview;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import com.squareup.timessquare.CalendarView;

import com.nuocf.yshuobang.R;

/**
 * @author luquan
 * 
 *         2014-7-28 下午6:21:59
 */
public class CalendarDialog {

	private CalendarView calendar = null;
	private static CalendarDialog mCalendarDialog = null;

	public static CalendarDialog getInstance() {
		if (mCalendarDialog == null)
			mCalendarDialog = new CalendarDialog();
		return mCalendarDialog;
	}

	public Dialog getCalendarDialog(Activity act,
			OnSelectedDateListener selectedDateListener,Calendar date) {
		View view = act.getLayoutInflater().inflate(R.layout.calendar, null);
		calendar = (CalendarView) view.findViewById(R.id.calendar);
		Dialog mDialog = showDialog(act, view,
				R.style.no_title_dialog);
		mDialog.setCanceledOnTouchOutside(true);
		initEvent(selectedDateListener,date);
		return mDialog;
	}

	public Dialog showDialog(Context context, View view, int dialogStyle) {
		Dialog dialog = new Dialog(context, dialogStyle);
		dialog.setContentView(view);
		dialog.show();
		return dialog;
	}

	private void initEvent(final OnSelectedDateListener dateSelectedListener,Calendar date) {

		calendar.setOnDateSelectedListener(new CalendarView.OnDateSelectedListener() {

			@Override
			public void onDateUnselected(Date date) {

			}

			@Override
			public void onDateSelected(Date date) {
				dateSelectedListener.selectDate(date.getYear() + 1900,
						date.getMonth(), date.getDate());
			}
		});
		
		calendar.setOnMonthChangedListener(new CalendarView.OnMonthChangedListener() {
			@Override
			public void onChangedToPreMonth(Date dateOfMonth) {
				new GetCalendarsOfMonthTask(dateOfMonth).execute();
			}

			@Override
			public void onChangedToNextMonth(Date dateOfMonth) {
				new GetCalendarsOfMonthTask(dateOfMonth).execute();
			}
		});
		calendar.markDatesOfMonth(date.get(Calendar.YEAR),date.get(Calendar.MONTH), false, true, date.get(Calendar.DAY_OF_MONTH));
		calendar.setToDate(new Date(date.getTimeInMillis()));

	}

	class GetCalendarsOfMonthTask extends AsyncTask<Object, Object, String> {
		Date dateOfMonth;
		List<List<Calendar>> calsList;

		public GetCalendarsOfMonthTask(Date dateOfMonth) {
			this.dateOfMonth = dateOfMonth;
		}

		@Override
		protected String doInBackground(Object... params) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateOfMonth);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (calsList != null && calsList.size() > 1) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(dateOfMonth);
				calendar.markDatesOfMonth(cal.get(Calendar.YEAR),
						cal.get(Calendar.MONTH), false, true, calsList.get(0));
				calendar.markDatesOfMonth(cal.get(Calendar.YEAR),
						cal.get(Calendar.MONTH), true, false, calsList.get(1));
			}
		}
	}

	public interface OnSelectedDateListener {
		public void selectDate(int year, int month, int day);
	}
}
