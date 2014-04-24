package com.chughes.cavehunter;

import java.util.LinkedList;

import android.util.Log;

import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

public class CaveBuilder {
	
	private static CaveBuilder instance;
	private SimpleVector center = new SimpleVector();
	private SimpleVector[] lastpoints;
	private LinkedList<CaveSection> cave = new LinkedList<CaveSection>();
	private World w;
	
	public static CaveBuilder instance() {
		if (instance == null){
			instance = new CaveBuilder();
		}
		return instance;
	}
	
	public void initialize(World world){
		w = world;
		for (int i = 0; i < 32; i++) {
			addSegment(center);

		}

	}
	
	private void addSegment(SimpleVector center){
		SimpleVector[] points;
		if (cave.isEmpty()){
			points = CaveSection.randomPoints(center);
		}else{
			points = cave.getLast().getPoints();
		}
		center.x += (Math.random()*50);
		center.y += (Math.random()*50);
		center.x -= (Math.random()*50);
		center.y -= (Math.random()*50);
		center.z = center.z + 30;
		CaveSection newest = new CaveSection(center,points);
		
		cave.addLast(newest);
		w.addObject(newest);
	}
	
	public CaveSection getCurrentSegment(){
		if (!cave.isEmpty()){
			return cave.getFirst();
		}
		return null;
	}
	
	public synchronized void step(float z) {
		if (!cave.isEmpty()){
			CaveSection current = cave.getFirst();
			//Log.v("z", "Testing: "+current.getPosition().z+" : "+z);
			if (current.getPosition().z + 120 < z){
				w.removeObject(current);
				cave.removeFirst();
				//Log.v("z", "Removing: "+current.getPosition().z+" : "+z);
			}
		}
		if (z > center.z - 900){
			//Log.i("cave", "Adding "+z+":"+center.z);

			addSegment(center);

		}

	}

}
