package com.virtuel.world;

import java.util.HashMap;
import java.util.Map;

import com.virtuel.math.vec.Vec3;

public class ChunkManager extends ChunkContainer {

	private Map<Long, Chunk> Chunks = new HashMap<Long, Chunk>();
	
	public void setChunk(int chunkX, int chunkY, int chunkZ, Chunk chunk) {
		Chunks.put(new Vec3.i(chunkX, chunkY, chunkZ).combine(), chunk);
	}
	
	public Chunk getChunk(long chunkIndex) {
		return Chunks.get(chunkIndex);
	}
	
	public Chunk getChunk(int chunkX, int chunkY, int chunkZ) {
		return exists(chunkX, chunkY, chunkZ) ? Chunks.get(new Vec3.i(chunkX, chunkY, chunkZ).combine()) : null;
	}
	
	public boolean removeChunk(int chunkX, int chunkY, int chunkZ) {
		if (exists(chunkX, chunkY, chunkZ)) {
			Chunks.remove(new Vec3.i(chunkX, chunkY, chunkZ));
			return true;
		}
		return false;
	}

	
	public boolean exists(int chunkX, int chunkY, int chunkZ) {
		return Chunks.containsKey(new Vec3.i(chunkX, chunkY, chunkZ).combine());
	}
	
	public int getChunkCount() {
		return Chunks.size();
	}
	
}
