/*    */ package com.funcom.gameengine.model.chunks;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BinaryChunkConfig
/*    */ {
/*    */   public static final int CHUNK_WIDTH = 20;
/*    */   public static final int CHUNK_HEIGHT = 20;
/*    */   public static final String BINARY_CONFIG_FILE = "config.bunk";
/*    */   public static final String TILE_INDEX_FILE = "index.bunk";
/*    */   public static final String BINARY_EXTENSION = ".bunk";
/*    */   public static final String BINARY_MANAGED_FILE = "managed.bunk";
/*    */   public static final String MAP_OBJECT_FILE = "mapobjects.bunk";
/* 17 */   public static final String TEMP_PATH = System.getProperty("java.io.tmpdir") + "/funcom/";
/*    */   public static final String BINARY_REGION_FILE = "region.bunk";
/*    */   public static final String BINARY_TEX_ATLAS_EXTENSION = ".tai";
/*    */   
/*    */   public static String getBinaryChunkFilename(int x, int y) {
/* 22 */     return x + "_" + y + ".bunk";
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\chunks\BinaryChunkConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */