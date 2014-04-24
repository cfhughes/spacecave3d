package com.chughes.cavehunter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerometerController extends Controller implements SensorEventListener{

	private Sensor mAccelerometer;
	private Sensor mMagnetometer;

	private boolean mLastAccelerometerSet;
	private boolean mLastMagnetometerSet;

	private float[] mLastAccelerometer = new float[3];
	private float[] mLastMagnetometer = new float[3];

	private float[] mR = new float[9];
	private float[] mOrientation = new float[3];
	
	private SensorManager mSensorManager;
	
	public AccelerometerController(Context c) {
		super(c);
		mSensorManager = (SensorManager) c.getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
	}
	
	public void pause(){
		mSensorManager.unregisterListener(this);
	}
	
	public void resume() {
		mLastAccelerometerSet = false;
		mLastMagnetometerSet = false;
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
		mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_GAME);

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor == mAccelerometer) {
			System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
			mLastAccelerometerSet = true;
		} else if (event.sensor == mMagnetometer) {
			System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
			mLastMagnetometerSet = true;
		}
		if (mLastAccelerometerSet && mLastMagnetometerSet) {
			SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
			SensorManager.getOrientation(mR, mOrientation);
			
			roll = mOrientation[1]/40;
			pitch = (mOrientation[2]+1)/60;
			
		}
		
	}

}
