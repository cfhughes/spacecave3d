package com.chughes.cavehunter;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

import com.threed.jpct.Logger;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;

/**
 * A simple demo. This shows more how to use jPCT-AE than it shows how to write
 * a proper application for Android. It includes basic activity management to
 * handle pause and resume...
 * 
 * @author EgonOlsen
 * 
 */
public class Gameplay extends Activity {



	// Used to handle pause and resume...
	public static Gameplay master = null;
	
	private Ship ship;

	private GLSurfaceView mGLView;
	public GLSurfaceView getmGLView() {
		return mGLView;
	}

	private Renderer renderer = null;
	
	//private Controller controller = null;
	
	private World world = null;



	protected void onCreate(Bundle savedInstanceState) {
		master = this;
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		Logger.log("onCreate");


		Texture wall = new Texture(64, 64, new RGBColor(169, 103, 31));

		if (!TextureManager.getInstance().containsTexture("wall1"))TextureManager.getInstance().addTexture("wall1", wall);


		super.onCreate(savedInstanceState);
		mGLView = new GLSurfaceView(getApplication());

		mGLView.setEGLConfigChooser(new GLSurfaceView.EGLConfigChooser() {
			public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
				// Ensure that we get a 16bit framebuffer. Otherwise, we'll fall
				// back to Pixelflinger on some device (read: Samsung I7500)
				int[] attributes = new int[] { EGL10.EGL_DEPTH_SIZE, 16, EGL10.EGL_NONE };
				EGLConfig[] configs = new EGLConfig[1];
				int[] result = new int[1];
				egl.eglChooseConfig(display, attributes, configs, 1, result);
				return configs[0];
			}
		});
		

		
		
		renderer = new Renderer(this);
		mGLView.setRenderer(renderer);
		setContentView(mGLView);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		int uiOptions = getWindow().getDecorView().getSystemUiVisibility();

		if (Build.VERSION.SDK_INT >= 14) {
            uiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        if (Build.VERSION.SDK_INT >= 16) {
            uiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        if (Build.VERSION.SDK_INT >= 18) {
            uiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(uiOptions);


        

	}

	@Override
	protected void onPause() {
		super.onPause();
		mGLView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mGLView.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

}
