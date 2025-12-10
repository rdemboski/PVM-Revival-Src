package com.funcom.gameengine.model.token;

import com.funcom.gameengine.WorldCoordinate;
import com.funcom.gameengine.model.factories.XmlMk6Tags;

public interface ChunkLoaderStrategy extends XmlMk6Tags {
  void process(ChunkLoaderToken paramChunkLoaderToken, WorldCoordinate paramWorldCoordinate);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\ChunkLoaderStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */