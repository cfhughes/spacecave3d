package com.chughes.cavehunter;

import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;

public class CaveSection extends Object3D {

	private static final long serialVersionUID = 8047683320744856124L;
	private SimpleVector position = new SimpleVector();
	private SimpleVector[] points;
	private SimpleVector[] startingpoints;
	

	/**
	 * @param center The central point the segment is moving towards
	 * @param startingpoints The connection points for the beginning of the segment
	 */
	public CaveSection(SimpleVector center, SimpleVector[] startingpoints) {
		super(16);
		position = new SimpleVector(center);
		this.startingpoints = startingpoints;
		randomGenerate();
		setTexture("wall1");
		setCollisionMode(COLLISION_CHECK_OTHERS);
	}

	private void randomGenerate(){
		points = randomPoints(position);
		for (int i = 0; i < points.length; i++) {
			int next = i+ 1;
			if (next==8)next=0;
			addTriangle(points[i], points[next], startingpoints[i]);
			addTriangle(points[next], startingpoints[next], startingpoints[i]);
		}
	}

	/** 
	 * Creates a circular, random shape consisting of 8 points around the given center to be used 
	 * as the intersection points for cave segments
	 * @param center The central point
	 * @return randomly generated points around the center 
	 */
	public static SimpleVector[] randomPoints(SimpleVector center){
		SimpleVector[] points = new SimpleVector[8];

		points[0] = new SimpleVector(center.x-100+(Math.random()*20),center.y,center.z);
		points[1] = new SimpleVector(center.x-70+(Math.random()*20),center.y-70+(Math.random()*20),center.z);
		points[2] = new SimpleVector(center.x,center.y-100+(Math.random()*20),center.z);
		points[3] = new SimpleVector(center.x+70-(Math.random()*20),center.y-70+(Math.random()*20),center.z);
		points[4] = new SimpleVector(center.x+100-(Math.random()*20),center.y,center.z);
		points[5] = new SimpleVector(center.x+70-(Math.random()*20),center.y+70-(Math.random()*20),center.z);
		points[6] = new SimpleVector(center.x,center.y+100-(Math.random()*20),center.z);
		points[7] = new SimpleVector(center.x-70+(Math.random()*20),center.y+70-(Math.random()*20),center.z);

		return points;
	}

	public SimpleVector getPosition() {
		return position;
	}

	public void setPosition(SimpleVector position) {
		this.position = position;
	}
	
	public SimpleVector[] getPoints() {
		return points;
	}

	public void setPoints(SimpleVector[] points) {
		this.points = points;
	}

}
