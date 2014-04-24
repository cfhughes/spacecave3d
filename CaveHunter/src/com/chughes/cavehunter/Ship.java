package com.chughes.cavehunter;

import java.io.InputStream;

import com.threed.jpct.Loader;
import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;

public class Ship extends Object3D {

	public Ship(int maxTriangles) {
		super(maxTriangles);
		// TODO Auto-generated constructor stub
	}

	public Ship(Object3D o3d) {
		super(o3d);
		setCollisionMode(Object3D.COLLISION_CHECK_SELF);
	}

	public static Ship load(InputStream stream, float scale) {
		Object3D[] model = Loader.load3DS(stream, scale);
		Object3D o3d = new Ship(0);
		Object3D temp = null;
		for (int i = 0; i < model.length; i++) {
			temp = model[i];
			temp.setCenter(SimpleVector.ORIGIN);
			temp.rotateX((float)( -.5*Math.PI));
			temp.rotateMesh();
			temp.setRotationMatrix(new Matrix());
			o3d = Object3D.mergeObjects(o3d, temp);
			o3d.build();
		}
		return new Ship(o3d);

	}

}
