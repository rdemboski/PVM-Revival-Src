/*    */ package com.funcom.gameengine.model.chunks;
/*    */ 
/*    */ public class ChunkConfig
/*    */ {
/*    */   public static final int CHUNK_WIDTH = 20;
/*    */   public static final int CHUNK_HEIGHT = 20;
/*    */   public static final String CONFIG_FILE = "config.xml";
/*    */   public static final String TILE_INDEX_FILE = "index.chunk";
/*    */   public static final String XML_EXTENSION = ".xml";
/*    */   public static final String CHUNK_EXTENSION = ".chunk";
/*    */   public static final String MANAGED_FILE = "managed.chunk";
/* 12 */   public static final String TEMP_PATH = System.getProperty("java.io.tmpdir") + "/funcom/";
/*    */   public static final String MAP_OBJECT_FILE = "mapobjects.chunk";
/*    */   public static final String PATHGRAPH_FILE = "pathgraph.pthgph";
/*    */   public static final String BLOOM_SETTINGS_FILE = "bloom.settings";
/*    */   public static final String MAP_REGION_FILE = "region.chunk";
/*    */   public static final String BINARY_TEX_ATLAS_EXTENSION = ".tai";
/*    */   
/*    */   public static String getChunkFilename(int x, int y) {
/* 20 */     return x + "_" + y + ".chunk";
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\chunks\ChunkConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */