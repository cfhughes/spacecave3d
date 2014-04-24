package com.chughes.cavehunter;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.preference.PreferenceManager;
import android.view.View.OnTouchListener;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import com.threed.jpct.util.MemoryHelper;

class Renderer implements GLSurfaceView.Renderer {

	private Gameplay g;
	private World world;
	private FrameBuffer fb;
	private Light sun = null;
	private Ship ship;
	private long time;
	private RGBColor back = new RGBColor(169, 103, 31);
	private Context context;
	private long scoreupdated;
	private Bitmap scorebit;
	private Controller controller;


	public Renderer(Gameplay g) {
		this.g = g;
		context = g.getApplicationContext();
	}

	public void onSurfaceChanged(GL10 gl, int w, int h) {
		if (fb != null) {
			fb.dispose();
		}
		fb = new FrameBuffer(gl, w, h);

		world = new World();
		world.setAmbientLight(50, 50, 50);

		g.setWorld(world);

		sun = new Light(world);
		sun.setIntensity(250, 250, 250);

		// Create a texture out of the icon...:-)
		//Texture texture = new Texture(BitmapHelper.rescale(BitmapHelper.convert(getResources().getDrawable(R.drawable.icon)), 64, 64));
		//TextureManager.getInstance().addTexture("texture", texture);

		InputStream stream;
		try {
			stream = context.getAssets().open("ship3.3ds");
			ship = Ship.load(stream, 3.0f);
			g.setShip(ship);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ship.setCenter(new SimpleVector(0,-40,10));
		ship.rotateY(3.14159f);
		ship.build();

		CaveBuilder.instance().initialize(world);

		world.addObject(ship);

		Camera cam = world.getCamera();
		cam.moveCamera(Camera.CAMERA_MOVEOUT, 50);
		cam.lookAt(ship.getTransformedCenter());

		SimpleVector sv = new SimpleVector();
		sv.set(ship.getTransformedCenter());
		sv.y -= 100;
		sv.z -= 100;
		sun.setPosition(sv);
		MemoryHelper.compact();
		
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(g);
		
		String control = sharedPrefs.getString("control", "accelerometer");
		
		if (control.equals("accelerometer")){
			controller = new AccelerometerController(g);
		}else{
			controller = new ScreenController(g, w, h);
			g.getmGLView().setOnTouchListener((OnTouchListener) controller);
		}

	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	}

	public void onDrawFrame(GL10 gl) {
		if (time != 0){
			float elapsed = System.currentTimeMillis() - time;
			
			ship.rotateAxis(ship.getZAxis(), -elapsed * controller.getRoll()/5);
			ship.rotateAxis(ship.getXAxis(), -elapsed * controller.getPitch()/5);
			
			
			SimpleVector pos = ship.getTransformedCenter();
			SimpleVector v = ship.getZAxis();
			v.scalarMul(70);
			pos.add(v);
			
			world.getCamera().setPosition(pos);
			
			v.set(-v.x, -v.y, -v.z);
			SimpleVector up = ship.getYAxis();
			up.set(-up.x, -up.y, -up.z);
//			world.getCamera().lookAt(ship.getTransformedCenter());
			world.getCamera().setOrientation(v, up);
			
			v = ship.getZAxis();
			v.set(-v.x,-v.y,-v.z);
			v.scalarMul(elapsed/10);
			v = ship.checkForCollisionEllipsoid(v, new SimpleVector(10f, 10f, 10f), 5);
			
			ship.translate(v);
			world.getCamera().moveCamera(v, 1);
			sun.setPosition(world.getCamera().getPosition());

		}
		time = System.currentTimeMillis();

		CaveBuilder.instance().step(ship.getTransformedCenter().z);
		ScoreManager.instance().step(ship.getTransformedCenter().z);
		if (time > scoreupdated + 2000){
			scoreupdated = time;
			
			if (scorebit != null){
				TextureManager.getInstance().removeAndUnload("score", fb);
			}
			
			scorebit = ScoreManager.instance().scoreImage();
			Texture score = new Texture(scorebit);
			TextureManager.getInstance().addTexture("score", score);
		}
		fb.clear(back);

		world.renderScene(fb);
		world.draw(fb);

		fb.blit(TextureManager.getInstance().getTexture("score"), 0, 0, 0, 0, 512, 256, true);

		fb.display();

		

	}
}
