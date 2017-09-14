package com.example.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Sample Stopwatch Android activity
 */
public class StopWatchActivity extends Activity {
	
	/**
	 * REFRESH_RATE defines how often we should update the timer to show how much time has elapsed.
	 * refresh every 100 milliseconds
	 */
	private final int REFRESH_RATE = 100;
	
	/**
	 * A variable to keep track of the seconds
	 */
	private static long timeStart;
	private int STATUS = RESET;
	private static final int NOT_RESET = 1;
	private static final int RESET = 0;
	
	
	private Handler mHandler = new Handler();
	
	
	Button btn_stop, btn_start, btn_reset;
	TextView timer, timerMs;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stopwatch);
		btn_stop = (Button) findViewById(R.id.btn_stop);
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_reset = (Button) findViewById(R.id.btn_reset);
		timer = (TextView) findViewById(R.id.timerView);
		timerMs = (TextView) findViewById(R.id.timerMsView);
	}
	
	
	// TODO: Create a Stopwatch!
	
	/**
	 * This method will start the current stopwatch clock
	 *
	 * @param view the current view
	 */
	
	public void startClick(View view) {
		hideStopButton();
		if (STATUS == RESET) {
			timeStart = System.currentTimeMillis();
			STATUS = NOT_RESET;
			
		}
		mHandler.post(startTimer);
	}
	
	/**
	 * This method will reset the current stopwatch clock
	 *
	 * @param view the current view
	 */
	public void resetClick(View view) {
		timeStart = 0;
		timer.setText(R.string.timer);
		timerMs.setText(R.string.timerMs);
		STATUS = RESET;
	}
	
	/**
	 * This method will stop the current stopwatch.
	 *
	 * @param view the current view
	 */
	public void stopClick(View view) {
		showStopButton();
		mHandler.removeCallbacks(startTimer);
	}
	
	/**
	 * This method will show the stop button when called by hiding the
	 * start and reset button and making the stop button visible.
	 */
	private void showStopButton() {
		btn_stop.setVisibility(View.GONE);
		btn_reset.setVisibility(View.VISIBLE);
		btn_start.setVisibility(View.VISIBLE);
	}
	
	/**
	 * This method will show the start and reset buttons by hiding the
	 * stop button and making the start and reset buttons visible.
	 */
	private void hideStopButton() {
		btn_start.setVisibility(View.GONE);
		btn_reset.setVisibility(View.GONE);
		btn_stop.setVisibility(View.VISIBLE);
	}
	
	/**
	 * Converts the elapsed given time and updates the display
	 *
	 * @param time the time to update the current display to
	 */
	private void updateTimer(long time) {
		
		StringBuilder timerS = new StringBuilder();
		StringBuilder timerMsS = new StringBuilder();
		
		
		long secT = time / 1000;
		long minT = secT / 60;
		long hourT = minT / 60;
		
		int sec = (int) secT % 60;
		int min = (int) minT % 60;
		int hour = (int) hourT % 24;
		
		int ms = (int) (time / 100) % 10;
		
		if (hour == 0) {
			timerS.append("00:");
		} else {
			if (hour >= 10) {
				timerS.append(hour + ":");
			} else {
				timerS.append("0").append(hour);
			}
		}
		
		if (min == 0) {
			timerS.append("00:");
		} else {
			if (min >= 10) {
				timerS.append(min + ":");
			} else {
				timerS.append("0").append(min).append(":");
			}
		}
		
		if (sec == 0) {
			timerS.append("00");
		} else {
			if (sec >= 10) {
				timerS.append(sec);
			} else {
				timerS.append("0").append(sec);
			}
		}
		
		if (ms == 0) {
			timerMsS.append(".0");
		} else {
			timerMsS.append(".").append(ms);
		}
		
		
		timer.setText(timerS.toString());
		timerMs.setText(timerMsS.toString());
	}
	
	/**
	 * Create a Runnable startTimer that makes timer runnable.
	 */
	private Runnable startTimer = new Runnable() {
		public void run() {
			
			long systime = System.currentTimeMillis() - timeStart;
			
			updateTimer(systime);
			
			mHandler.postDelayed(this, REFRESH_RATE);
		}
	};
	
}
    