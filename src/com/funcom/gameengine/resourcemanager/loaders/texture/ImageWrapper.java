package com.funcom.gameengine.resourcemanager.loaders.texture;

import com.jme.image.Texture;
import java.nio.ByteBuffer;

public interface ImageWrapper {
  int getOriginalWidth();
  
  int getOriginalHeight();
  
  int getPower2Width();
  
  int getPower2Height();
  
  int getBytesPerPixel();
  
  Texture loadTexture(boolean paramBoolean) throws InterruptedException;
  
  void setTargetBuffer(ByteBuffer paramByteBuffer);
  
  ByteBuffer getTargetBuffer();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\texture\ImageWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */