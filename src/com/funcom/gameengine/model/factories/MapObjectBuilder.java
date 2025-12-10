/*    */ package com.funcom.gameengine.model.factories;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.model.chunks.ChunkWorldInfo;
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.funcom.gameengine.resourcemanager.loaders.BinaryLoader;
/*    */ import com.funcom.gameengine.utils.SpatialUtils;
/*    */ import com.funcom.gameengine.view.MapObject;
/*    */ import java.io.File;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.jdom.DataConversionException;
/*    */ import org.jdom.Document;
/*    */ import org.jdom.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapObjectBuilder
/*    */ {
/*    */   private List<MapObject> mapObjectList;
/*    */   private ResourceGetter resourceGetter;
/*    */   
/*    */   public MapObjectBuilder(ResourceGetter resourceGetter) {
/* 32 */     this.resourceGetter = resourceGetter;
/*    */   }
/*    */   
/*    */   public List<MapObject> getMapObjectList(ChunkWorldInfo chunkWorldInfo) {
/* 36 */     if (this.mapObjectList == null) {
/* 37 */       this.mapObjectList = new ArrayList<MapObject>();
/*    */       
/* 39 */       ByteBuffer blob = this.resourceGetter.getBlob((new File("binary", chunkWorldInfo.getBasePath())).getPath().replace('\\', '/') + "/" + "mapobjects.bunk", CacheType.CACHE_TEMPORARILY);
/* 40 */       Document document = BinaryLoader.convertBlobToMap(blob, this.resourceGetter);
/*    */       
/* 42 */       Element rootElement = document.getRootElement();
/* 43 */       List<Element> childElements = rootElement.getChildren();
/* 44 */       for (Element childElement : childElements) {
/* 45 */         String childName = childElement.getAttributeValue("name");
/* 46 */         WorldCoordinate coord = null;
/*    */         try {
/* 48 */           coord = SpatialUtils.getElementWorldCoordinate(childElement, chunkWorldInfo.getMapId());
/* 49 */         } catch (DataConversionException e) {
/* 50 */           e.printStackTrace();
/*    */         } 
/* 52 */         String imagePath = childElement.getAttributeValue("value");
/* 53 */         if (!imagePath.equalsIgnoreCase(MapObject.MapObjectType.MAP_PORTAL_TYPE.getIcon()))
/* 54 */           this.mapObjectList.add(new MapObject(childName, coord, imagePath)); 
/*    */       } 
/*    */     } 
/* 57 */     return this.mapObjectList;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\factories\MapObjectBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */