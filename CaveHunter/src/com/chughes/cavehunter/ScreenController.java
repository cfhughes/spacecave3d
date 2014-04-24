package com.chughes.cavehunter;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class ScreenController extends Controller implements OnTouchListener {

	private int screen_h;
	private int screen_w;

	private int center_x;
	private int center_y;

	public ScreenController(Context c,int w, int h) {
		super(c);
		screen_w = w;
		screen_h = h;

		center_x = w/6;
		center_y = h * 5/6;

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == event.ACTION_UP){
			pitch = 0;
			roll = 0;
		}else{
			float x = event.getX();
			float y = event.getY();
			pitch = -(y - center_y)/40000;
			roll = -(x - center_x)/40000;
		}
		return true;
	}

}
