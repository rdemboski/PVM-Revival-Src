/*    */ package com.funcom.gameengine.audio;
/*    */ 
/*    */ import com.funcom.audio.DataLoader;
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManagerException;
/*    */ import com.funcom.util.DebugManager;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ResourceDataLoader
/*    */   implements DataLoader {
/*    */   private static final Map<Object, Object> directBufferProps;
/*    */   
/*    */   static {
/* 18 */     HashMap<Object, Object> tmp = new HashMap<Object, Object>();
/* 19 */     tmp.put("direct", Boolean.valueOf(true));
/* 20 */     directBufferProps = Collections.unmodifiableMap(tmp);
/*    */   }
/*    */   
/*    */   private final ResourceManager resourceManager;
/*    */   private final String baseDir;
/*    */   
/*    */   public ResourceDataLoader(ResourceManager resourceManager, String baseDir) {
/* 27 */     this.resourceManager = resourceManager;
/* 28 */     if (!baseDir.endsWith("\\") && !baseDir.endsWith("/"))
/*    */     {
/* 30 */       baseDir = baseDir + "/";
/*    */     }
/* 32 */     this.baseDir = baseDir;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer load(String path) {
/* 37 */     String fullPath = this.baseDir + path;
/* 38 */     ByteBuffer buffer = null;
/*    */     try {
/* 40 */       buffer = (ByteBuffer)this.resourceManager.getResource(ByteBuffer.class, fullPath, CacheType.NOT_CACHED, directBufferProps);
/* 41 */     } catch (ResourceManagerException e) {
/* 42 */       DebugManager.getInstance().warn("Missing audio data: fullPath=" + fullPath, (Exception)e);
/*    */     } 
/* 44 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\audio\ResourceDataLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */