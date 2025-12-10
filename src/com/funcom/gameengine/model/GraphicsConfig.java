/*    */ package com.funcom.gameengine.model;
/*    */ 
/*    */ import com.funcom.gameengine.jme.BlendMode;
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GraphicsConfig
/*    */ {
/*    */   private static final String MODEL_GFXCONFIG = "/model.gfxconfig";
/*    */   private final BlendMode blendMode;
/*    */   
/*    */   public GraphicsConfig() {
/* 17 */     this.blendMode = BlendMode.NORMAL;
/*    */   }
/*    */   
/*    */   public GraphicsConfig(BlendMode blendMode) {
/* 21 */     this.blendMode = blendMode;
/*    */   }
/*    */   
/*    */   public BlendMode getBlendMode() {
/* 25 */     return this.blendMode;
/*    */   }
/*    */   
/*    */   public static GraphicsConfig getBillboardConfig(ResourceGetter resourceGetter, String resourceName) {
/* 29 */     String filename = "/billboard.gfxconfig";
/* 30 */     return getConfig(resourceGetter, resourceName, filename);
/*    */   }
/*    */   
/*    */   public static GraphicsConfig getModelConfig(ResourceGetter resourceGetter, String resourceName) {
/* 34 */     String filename = "/model.gfxconfig";
/* 35 */     return getConfig(resourceGetter, resourceName, filename);
/*    */   }
/*    */   
/*    */   public static GraphicsConfig getModelConfig(ResourceManager resourceManager, String resourceName) {
/* 39 */     String filename = "/model.gfxconfig";
/*    */     
/* 41 */     return getConfig(resourceManager, resourceName, filename);
/*    */   }
/*    */   
/*    */   private static GraphicsConfig getConfig(ResourceManager resourceManager, String resourceName, String filename) {
/* 45 */     String docName = makeConfigPath(resourceName, filename);
/*    */     
/* 47 */     return (GraphicsConfig)resourceManager.getResource(GraphicsConfig.class, docName, CacheType.CACHE_TEMPORARILY);
/*    */   }
/*    */   
/*    */   private static GraphicsConfig getConfig(ResourceGetter resourceGetter, String resourceName, String filename) {
/* 51 */     String docName = makeConfigPath(resourceName, filename);
/*    */     
/* 53 */     return resourceGetter.getGraphicsConfig(docName);
/*    */   }
/*    */   
/*    */   private static String makeConfigPath(String resourceName, String filename) {
/* 57 */     int endIndex = Math.max(resourceName.lastIndexOf('\\'), resourceName.lastIndexOf('/'));
/* 58 */     if (endIndex == -1) {
/* 59 */       endIndex = 0;
/*    */     }
/* 61 */     return resourceName.substring(0, endIndex) + filename;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\GraphicsConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */