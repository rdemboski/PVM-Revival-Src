package com.funcom.gameengine.model.chunks;

import com.funcom.gameengine.WorldCoordinate;

public interface ChunkListener {
  void newChunk(String paramString, WorldCoordinate paramWorldCoordinate, ChunkWorldNode paramChunkWorldNode);
  
  void addedChunk(ChunkNode paramChunkNode);
  
  void removedChunk(ChunkNode paramChunkNode);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\chunks\ChunkListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */