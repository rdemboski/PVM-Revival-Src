package com.funcom.gameengine.jme.modular;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ModularDescription {
  void addChangedListener(ModularChangedListener paramModularChangedListener);
  
  void removeChangedListener(ModularChangedListener paramModularChangedListener);
  
  Set<Part> getBodyParts();
  
  Set<String> getBodyPartNames();
  
  Part getBodyPart(String paramString);
  
  Set<Animation> getAnimations();
  
  float getScale();
  
  void dispose();
  
  Part getPetModel();
  
  public static interface ModularChangedListener {
    void partChanged(String param1String);
  }
  
  public static interface Animation {
    String getPetAnimationPath();
    
    String getPlayerAnimationPath();
    
    String getAnimationName();
    
    int getPlayerFrameRate();
    
    int getPetFrameRate();
  }
  
  public static interface TextureLoaderDescription {
    String getId();
    
    Map<String, String> getParams();
  }
  
  public static interface TexturePart {
    String getTextureMap();
    
    boolean isTransparent();
    
    int getTextureLayerCount();
    
    ModularDescription.TextureLoaderDescription getTextureLoaderDescription();
    
    List<String> getTextureLayers();
  }
  
  public static interface Part {
    String getPartName();
    
    boolean isVisible();
    
    String getMeshPath();
    
    List<ModularDescription.TexturePart> getTextureParts();
  }
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\modular\ModularDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */