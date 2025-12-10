package com.funcom.gameengine.resourcemanager.loaders.texture;

public interface TextureData {
  String getName();
  
  void load() throws InterruptedException;
  
  void init();
  
  void finish() throws InterruptedException;
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\texture\TextureData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */