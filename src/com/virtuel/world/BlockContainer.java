package com.virtuel.world;

public class BlockContainer {
	
	private int BlockCount = 0;
	protected int[] Blocks;
	private boolean built = true;
	
	public int getBlockID(int x, int y, int z) {
		try {
			int index = x << Chunk.SHIFT*2 | y << Chunk.SHIFT | z;
			return Blocks[index];
		} catch (IndexOutOfBoundsException ex) {
			return -1;
		}
	}
	
	public boolean setBlockID(int x, int y, int z, int id) {
		try {
			int index = x << Chunk.SHIFT*2 | y << Chunk.SHIFT | z;
			if (Blocks[index] != 0 && id == 0) BlockCount--;
			else if (Blocks[index] == 0 && id != 0) BlockCount++;
			if (Blocks[index] == id) return false;
			built = false;
			Blocks[index] = id;
			return true;
		} catch (IndexOutOfBoundsException ex) {
			return false;
		}
	}

	public int[] getBlocks() {
		return Blocks;
	}
	
	public boolean isEmpty() {
		return BlockCount == 0;
	}
}
