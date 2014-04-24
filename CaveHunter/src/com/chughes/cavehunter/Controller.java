package com.chughes.cavehunter;

import android.content.Context;

public abstract class Controller {
	protected float roll;
	protected float pitch;

	public Controller(Context c) {
	}

	public void pause() {
	}
	
	public void resume() {
	}

	float getPitch() {
		return pitch;
	}
	float getRoll() {
		return roll;
	}
}
