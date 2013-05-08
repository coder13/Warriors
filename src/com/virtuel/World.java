package com.virtuel;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.lwjgl.opengl.GL11;

import com.virtuel.math.vec.Vec3;
import com.virtuel.rendering.BlockRenderer;
import com.virtuel.util.GLUtil;
import com.virtuel.world.Chunk;
import com.virtuel.world.ChunkGenerator;
import com.virtuel.world.ChunkManager;

import static com.virtuel.math.VMath.*;


public class World {

	public static int HBounds=15;
	public static int VBounds=13;
	
	private ChunkManager chunkManager;
	private List<Long> activeChunks;
	
	public World() {
		chunkManager = new ChunkManager();
		activeChunks = new ArrayList<Long>();
	}
	
	public void reset() {
		chunkManager = new ChunkManager();
		activeChunks.clear();
	}
	
	
	public int getBlockID(int x, int y, int z) {
		Chunk chunk = chunkManager.getChunk(x >> Chunk.SHIFT, y >> Chunk.SHIFT, z >> Chunk.SHIFT);
		return chunk!=null ? (chunk.getBlockID(x&Chunk.lSIZE, y&Chunk.lSIZE, z&Chunk.lSIZE)) : 0;
	}
	
	
	public Chunk generateChunk(int chunkX, int chunkY, int chunkZ) {
		Chunk chunk = ChunkGenerator.generateSin(chunkX, chunkY, chunkZ);
		chunkManager.setChunk(chunkX, chunkY, chunkZ, chunk);
		return chunk;
	}
	
	public boolean loadChunk(int chunkX, int chunkY, int chunkZ) {
		Chunk chunk = chunkManager.getChunk(chunkX, chunkY, chunkZ);
		if (chunk == null) {
			chunk = generateChunk(chunkX, chunkY, chunkZ);
		}
		if (!activeChunks.contains(chunk.getIndex()))
			 activeChunks.add(chunk.getIndex());
		return false;
	}
	
	public boolean unLoadChunk(int chunkX, int chunkY, int chunkZ) {
		Chunk chunk = chunkManager.getChunk(chunkX, chunkY, chunkZ);
		if (chunk != null) {
			activeChunks.remove(chunk.getIndex());
			return true;
		}
		return false;
	}
	
	
	public void update(Player player) {
		Vec3.i pos = player.getBlockPosition().div(new Vec3.i(Chunk.SIZE, Chunk.SIZE, Chunk.SIZE));
		
		activeChunks.clear();
		
		for (int x = -HBounds/2; x < HBounds/2; x++) {
			for (int y = -VBounds/2; y < VBounds/2; y++) {
				for (int z = -HBounds/2; z < HBounds/2; z++) {
					loadChunk(pos.X + x, pos.Y + y, pos.Z + z);
				}
			}
		}
		
	}
	
	
	public void renderAll() {
		renderBlocks();
	}
	
	public void renderBlocks() {
		int id;
		int displayList, x, y, z;
		IntBuffer buffer = GLUtil.createIntBuffer(activeChunks.size());
		
		for (Long index : activeChunks) {
			Chunk chunk = chunkManager.getChunk(index);
			if (chunk.isEmpty()) continue;
			displayList = chunk.getDisplayList();
			if (displayList == -1) {
				chunk.setDisplayList(displayList = GL11.glGenLists(1));
				GL11.glNewList(displayList, GL11.GL_COMPILE_AND_EXECUTE);
	
					int offSetX = chunk.getX()*Chunk.SIZE, offSetY = chunk.getY()*Chunk.SIZE, offSetZ = chunk.getZ()*Chunk.SIZE;
					BlockRenderer.beginDrawing();
						int[] blocks = chunk.getBlocks();
						for (int i = 0; i < blocks.length; i++) {
							if ((id = blocks[i]) == 0) continue;
							x = (i>>Chunk.SHIFT*2)&Chunk.lSIZE; y = (i>>Chunk.SHIFT)&Chunk.lSIZE; z = i&Chunk.lSIZE;
							BlockRenderer.renderBlock(offSetX + x, offSetY + y, offSetZ + z, id);
						}
					BlockRenderer.draw();
					
				GL11.glEndList();
			} 
			else buffer.put(displayList);
		}
		
		buffer.flip();
		GL11.glCallLists(buffer);
	}
	
	public int getChunkCount() {
		return activeChunks.size();
	}
	
	public int getTotalChunkCount() {
		return chunkManager.getChunkCount();
	}
	
}
