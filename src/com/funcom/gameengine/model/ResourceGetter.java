package com.funcom.gameengine.model;

import com.funcom.commons.jme.md5importer.JointAnimation;
import com.funcom.commons.jme.md5importer.ModelNode;
import com.funcom.gameengine.jme.TileQuadCached;
import com.funcom.gameengine.resourcemanager.CacheType;
import com.funcom.gameengine.resourcemanager.TileInfo;
import com.funcom.gameengine.resourcemanager.loadingmanager.TextureAtlasDescription;
import com.jme.image.Texture;
import com.jme.scene.Node;
import com.jme.scene.state.GLSLShaderObjectsState;
import com.jmex.bui.BCursor;
import com.jmex.bui.BImage;
import com.jmex.bui.icon.BIcon;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;

public interface ResourceGetter {
  Document getDocument(String paramString, CacheType paramCacheType);
  
  GraphicsConfig getGraphicsConfig(String paramString);
  
  Texture getTexture(String paramString, CacheType paramCacheType);
  
  Texture getTextureCopy(String paramString);
  
  Node getStaticModel(String paramString);
  
  ModelNode getModelNode(String paramString);
  
  JointAnimation getModelAnimation(String paramString);
  
  GLSLShaderObjectsState getShader(String paramString);
  
  BImage getBImage(String paramString);
  
  BIcon getBIcon(String paramString);
  
  BCursor getBCursor(String paramString);
  
  ByteBuffer getBlob(String paramString);
  
  ByteBuffer getBlob(String paramString, CacheType paramCacheType);
  
  boolean exists(String paramString);
  
  TileQuadCached.CachedTileData getCachedTileData(String paramString);
  
  List<String> listFiles(String paramString1, String paramString2);
  
  TextureAtlasDescription getTextureAtlasDescription(String paramString, CacheType paramCacheType);
  
  void setTileCache(ArrayList<TileInfo> paramArrayList);
  
  void clearTileCache();
  
  TileInfo getTile(int paramInt);
  
  TileInfo getTile(String paramString1, String paramString2, String paramString3, String paramString4);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\ResourceGetter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */