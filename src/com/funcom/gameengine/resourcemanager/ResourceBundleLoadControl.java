/*    */ package com.funcom.gameengine.resourcemanager;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Arrays;
/*    */ import java.util.Enumeration;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ class ResourceBundleLoadControl extends ResourceBundle.Control {
/*    */   private static final int DO_NOT_CACHE = -2;
/*    */   private final ResourceManager parent;
/*    */   private final Class resourceType;
/*    */   private final CacheType cacheInMemory;
/*    */   
/*    */   public ResourceBundleLoadControl(ResourceManager parent, Class resourceType, CacheType cacheInMemory) {
/* 17 */     this.parent = parent;
/* 18 */     this.resourceType = resourceType;
/* 19 */     this.cacheInMemory = cacheInMemory;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getTimeToLive(String baseName, Locale locale) {
/* 24 */     return -2L;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getFormats(String baseName) {
/* 31 */     return Arrays.asList(new String[] { "tcgResource" });
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
/* 36 */     int lastDot = baseName.lastIndexOf('.');
/* 37 */     String fileMain = baseName;
/* 38 */     String fileExt = "";
/* 39 */     if (lastDot >= 0) {
/* 40 */       fileMain = baseName.substring(0, lastDot);
/* 41 */       fileExt = baseName.substring(lastDot);
/*    */     } 
/* 43 */     String filePath = toBundleName(fileMain, locale) + fileExt;
/*    */     try {
/* 45 */       Object resource = this.parent.getResource(this.resourceType, filePath, this.cacheInMemory);
/* 46 */       return new SingleResourceBundle(resource);
/* 47 */     } catch (ResourceManagerException e) {
/* 48 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   private static class SingleResourceBundle extends ResourceBundle {
/*    */     private final Object resource;
/*    */     
/*    */     public SingleResourceBundle(Object resource) {
/* 56 */       this.resource = resource;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     protected Object handleGetObject(String key) {
/* 62 */       return this.resource;
/*    */     }
/*    */ 
/*    */     
/*    */     public Enumeration<String> getKeys() {
/* 67 */       return null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\ResourceBundleLoadControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */