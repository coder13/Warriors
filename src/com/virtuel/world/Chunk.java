package com.virtuel.world;
import com.virtuel.math.vec.Vec3;

public class Chunk extends BlockContainer {
	
	public static int SIZE  = 16, lSIZE = SIZE-1;
	public static int SHIFT = Integer.numberOfTrailingZeros(SIZE);
	
	private int X, Y, Z;
	private long Index;
	private int DisplayList;
	
	public Chunk(int chunkX, int chunkY, int chunkZ) {
		Blocks = new int[SIZE*SIZE*SIZE];
		X = chunkX;
		Y = chunkY;
		Z = chunkZ;
		Index = new Vec3.i(chunkX, chunkY, chunkZ).combine();
		DisplayList = -1;
	}
	
	
	public Vec3.i getPosition(){
		return new Vec3.i(X, Y, Z);
	}
	
	public int getX() {
		return X;
	}
	
	public int getY() {
		return Y;
	}

	public int getZ() {
		return Z;
	}
	
	public long getIndex() {
		return Index;
	}
	
	public int getDisplayList() {
		return DisplayList;
	}
	
	public void setDisplayList(int displayList) {
		DisplayList = displayList;
	}

}
