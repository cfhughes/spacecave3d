package com.chughes.cavehunter;

import java.text.NumberFormat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class ScoreManager {
	
	private static ScoreManager instance;
	private float score;
	private Bitmap bitmap = Bitmap.createBitmap(512, 256, Bitmap.Config.ARGB_4444);
	private Canvas canvas = new Canvas(bitmap);
	private Paint textPaint;
	private NumberFormat nf;
	
	public ScoreManager() {
		textPaint = new Paint();
		textPaint.setTextSize(70);
		textPaint.setAntiAlias(false);
		textPaint.setARGB(0xff, 0x11, 0x11, 0x11);
		
		nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(1);
	}
	
	public static ScoreManager instance(){
		if (instance == null){
			instance = new ScoreManager();
		}
		return instance;
	}

	public void step(float z) {
		score = z;
		
	}
	
	public Bitmap scoreImage(){
		
		bitmap.eraseColor(0);

		canvas.drawText("Score: "+nf.format(score), 16,82, textPaint);
		
		return bitmap;
	}

}
