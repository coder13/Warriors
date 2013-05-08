package com.virtuel.rendering;

import com.virtuel.World;
import com.virtuel.math.vec.Vec3;
import com.virtuel.world.Chunk;

public final class BlockRenderer {

	private static Tessellator tess = Tessellator.Instance;
	
	private static World currentWorld;
	
	public static void setWorld(World world) {
		currentWorld = world;
	}
	
	
	public static void beginDrawing() {
		tess.beginDrawingQuads();
	}

	public static int draw() {
		return tess.draw();
	}
	
	
	public static void renderBlock(int x, int y, int z, int id) {
		if (shouldRenderSide(x, y, z, 0)) { // Top Face
			
			tess.setColor(id);
			tess.addVertex(x+1, y+1, z+1);
			tess.addVertex(x+1, y+1, z  );
			tess.addVertex(x  , y+1, z  );
			tess.addVertex(x  , y+1, z+1);
			
		} if (shouldRenderSide(x, y, z, 1)) { // Bottom Face
			int sid = shade(id, .5);
			
			tess.setColor(sid);
			tess.addVertex(x,   y  , z+1);
			tess.addVertex(x  , y  , z  );
			tess.addVertex(x+1, y  , z  );
			tess.addVertex(x+1, y  , z+1);
			
		} if (shouldRenderSide(x, y, z, 2)) { // North Face
			int sid = shade(id, .6);

			tess.setColor(sid);
			tess.addVertex(x+1, y  , z+1);
			tess.addVertex(x+1, y  , z  );
			tess.addVertex(x+1, y+1, z  );
			tess.addVertex(x+1, y+1, z+1);
			
		} if (shouldRenderSide(x, y, z, 3)) { // South Face
			int sid = shade(id, .6);

			tess.setColor(sid);
			tess.addVertex(x  , y+1, z+1);
			tess.addVertex(x  , y+1, z  );
			tess.addVertex(x  , y  , z  );
			tess.addVertex(x  , y  , z+1);
			
		} if (shouldRenderSide(x, y, z, 4)) { // West Face
			int sid = shade(id, .8);

			tess.setColor(sid);
			tess.addVertex(x+1, y+1, z+1);
			tess.addVertex(x  , y+1, z+1);
			tess.addVertex(x  , y  , z+1);
			tess.addVertex(x+1, y  , z+1);
			
		} if (shouldRenderSide(x, y, z, 5)) { // East Face
			int sid = shade(id, .8);

			tess.setColor(sid);
			tess.addVertex(x+1, y  , z);
			tess.addVertex(x  , y  , z);
			tess.addVertex(x  , y+1, z);
			tess.addVertex(x+1, y+1, z);
			
		}
	}
	
	private static int shade(int color, double shade) {
		int r = (int)((double)((color>>16)&0xff)*shade),
			g = (int)((double)((color>> 8)&0xff)*shade),
			b = (int)((double)((color    )&0xff)*shade);
		return (0xff<<24) | (r<<16) | (g<<8) | b;//(color&15);
	}
	
	public static boolean shouldRenderSide(int x, int y, int z, int side) {
		return currentWorld.getBlockID(x+dirs[side].X, y+dirs[side].Y, z+dirs[side].Z) == 0;
	}
	
	private static Vec3.i[] dirs =  {
		new Vec3.i( 0, 1, 0), // Up
		new Vec3.i( 0,-1, 0), // Down
		new Vec3.i( 1, 0, 0), // North
		new Vec3.i(-1, 0, 0), // South
		new Vec3.i( 0, 0, 1), // West
		new Vec3.i( 0, 0,-1)  // East
	};
}
