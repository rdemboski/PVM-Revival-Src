package com.funcom.commons.jme;

import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Spatial;
import java.nio.IntBuffer;
import java.util.ArrayList;

public interface OffscreenRenderer {
  boolean isSupported();
  
  Camera getCamera();
  
  void setCamera(Camera paramCamera);
  
  void render(Spatial paramSpatial);
  
  void render(Spatial paramSpatial, boolean paramBoolean);
  
  IntBuffer getImageData();
  
  void render(ArrayList<? extends Spatial> paramArrayList);
  
  void render(ArrayList<? extends Spatial> paramArrayList, boolean paramBoolean);
  
  void setBackgroundColor(ColorRGBA paramColorRGBA);
  
  ColorRGBA getBackgroundColor();
  
  void cleanup();
  
  int getWidth();
  
  int getHeight();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\OffscreenRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */