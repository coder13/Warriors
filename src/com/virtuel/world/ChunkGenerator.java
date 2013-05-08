package com.virtuel.world;
import static com.virtuel.math.VMath.*;

public class ChunkGenerator {

	/* Legacy Generation
	 * 
	 * int rx = relX*relX, ry = relY*relY, rz = relZ*relZ;
	   if (rx+ry <= r || rz+ry <= r || relY == 0) {
	   		id = (short)(relY == 0 ? 0xdf : 0x7f);
	   		chunk.setBlockID(x, y, z, id);
	   }
	 * 
	 * 
	 */
	
	public static int noise2D(int x, int y) {
		int n = x + y * 57;
		n = (n<<13)^n;
		return 1 - ( (n * (n*n * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824;
	}
	
	public static int smoothNoise2D(int x, int y) {
		int corners = (noise2D(x-1,y-1) + noise2D(x+1,y-1) + noise2D(x+1,y+1) + noise2D(x-1,y+1));
		int sides   = (noise2D(x-1,y  ) + noise2D(x+1,y  ) + noise2D(x,  y+1) + noise2D(x,  y-1));
		int center  = noise2D(x,y);
		return (corners + sides + center)/3;
	}

	public static double _sin(double x, double y) {
		return (sin(abs(y)) * cos(abs(x)))*Chunk.SIZE;
	}
	
	public static int sin2D(int x, int y) {
		double corners = (_sin(x-1,y-1) + _sin(x+1,y-1) + _sin(x+1,y+1) + _sin(x-1,y+1))/Chunk.SHIFT;
		double sides   = (_sin(x-1,y  ) + _sin(x+1,y  ) + _sin(x,  y+1) + _sin(x,  y-1))/Chunk.SHIFT;
		double center  =  _sin(x,y);
		return (int)(corners + sides + center + -smoothNoise2D(x, y)*2)/Chunk.SHIFT + smoothNoise2D(x, y)/2;
	}
	
	public static Chunk generateSin(int chunkX, int chunkY, int chunkZ) {
		Chunk chunk = new Chunk(chunkX, chunkY, chunkZ);
		int height, id, bx, bz, by;
		int x, y, z;
		
		for (x = 0; x < Chunk.SIZE; x++) {
			bx = chunkX*Chunk.SIZE+x;
			for (z = 0; z < Chunk.SIZE; z++) {
				bz = chunkZ*Chunk.SIZE+z;
				height = sin2D(bx, bz)*Chunk.SHIFT;
				for (y = 0; y < Chunk.SIZE; y++) {
					by = chunkY*Chunk.SIZE+y;
					
					if (by == height)
						id = Block.Grass;
					else if (by >= height-5 && by <= height-1)
						id = Block.Dirt;
					else if (by <= height-5 && by > height-10)
						id = Block.Stone;
					else 
						id = 0;
					chunk.setBlockID(x, y, z, id);
				}
			}
		}
		
		return chunk;
	}
	
	public static Chunk generateNorm(int chunkX, int chunkY, int chunkZ) {
		Chunk chunk = new Chunk(chunkX, chunkY, chunkZ);
		int height, by;
		int id, x, y, z;
		for (int i = 0; i < Chunk.SIZE*Chunk.SIZE; i++) {
			x = (i>>Chunk.SHIFT) & Chunk.SHIFT;
			z = (i)				 & Chunk.SHIFT;	
			
			height = smoothNoise2D(chunkX*Chunk.SIZE+x, chunkZ*Chunk.SIZE+z);
			for (y = 0; y < Chunk.SIZE; y++) {
				by = chunkY*Chunk.SIZE+y;
				
				id = 0;
				if (by == height)
					id = Block.Grass;
				else if (by >= height-5 && by <= height-1)
					id = Block.Dirt;
				else if (by <= height-5)
					id = Block.Stone;
				chunk.setBlockID(x, y, z, id);
			}
		}
//		for (int x = 0; x < Chunk.SIZE; x++) {
//			for (int z = 0; z < Chunk.SIZE; z++) {
//				height = smoothNoise2D(chunkX*Chunk.SIZE+x, chunkZ*Chunk.SIZE+z);
//				for (int y = 0; y < Chunk.SIZE; y++) {
//					by = chunkY*Chunk.SIZE+y;
//					
//					id = 0;
//					if (by == height)
//						id = Block.Grass;
//					else if (by >= height-5 && by <= height-1)
//						id = Block.Dirt;
//					else if (by <= height-5)
//						id = Block.Stone;
//					chunk.setBlockID(x, y, z, id);
//				}
//			}
//		}
		
		return chunk;
	}
	
	public static int radius = 8;
	
	public static Chunk genChunkNorm(int chunkX, int chunkY, int chunkZ) {
		Chunk chunk = new Chunk(chunkX, chunkY, chunkZ);
		int relX, relY, relZ;
		short id = 0;
		int r = radius*radius;
		for (int x = 0; x < Chunk.SIZE; x++) {
			relX = chunkX*Chunk.SIZE+x;
			for (int z = 0; z < Chunk.SIZE; z++) {
				relZ = chunkZ*Chunk.SIZE+z;
				for (int y = 0; y < Chunk.SIZE; y++) {
					relY = chunkY*Chunk.SIZE+y;
					
					int rx = relX*relX, ry = relY*relY, rz = relZ*relZ;
					if (rx+ry <= r || rz+ry <= r || relY == 0) {
						id = (short)(relY == 0 ? 0xdf : 0x7f);
						chunk.setBlockID(x, y, z, id);
					}
				}
			}
		}
		return chunk;
	}
	
}