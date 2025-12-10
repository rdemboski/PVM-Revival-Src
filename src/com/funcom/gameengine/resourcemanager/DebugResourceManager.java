/*     */ package com.funcom.gameengine.resourcemanager;
/*     */ 
/*     */ import com.jme.system.DisplaySystem;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ 
/*     */ public class DebugResourceManager
/*     */   extends DefaultResourceManager
/*     */ {
/*     */   private static final String FILE = "c:/tcg.resourcedebug.txt";
/*     */   private PrintWriter out;
/*  20 */   private Set<String> resources = new HashSet<String>();
/*     */   private boolean initialized;
/*     */   private ResourceFilter filter;
/*     */   
/*     */   public DebugResourceManager() {
/*  25 */     readResources();
/*     */     
/*     */     try {
/*  28 */       this.out = new PrintWriter(new BufferedWriter(new FileWriter("c:/tcg.resourcedebug.txt", true)), true);
/*  29 */     } catch (IOException e) {
/*  30 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/*  33 */     this.filter = new ResourceFilter()
/*     */       {
/*     */         public boolean canPreload(String type, String path) {
/*  36 */           boolean isModelData = ((type.equals("com.funcom.commons.jme.md5importer.JointAnimation") || type.equals("com.funcom.commons.jme.md5importer.ModelNode")) && path.startsWith("characters/"));
/*     */           
/*  38 */           boolean isMapData = (type.equals("java.nio.ByteBuffer") && path.startsWith("binary/"));
/*  39 */           return (isModelData || isMapData);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private static interface ResourceFilter {
/*     */     boolean canPreload(String param1String1, String param1String2);
/*     */   }
/*     */   
/*     */   public void clearUnusedResources(CacheType cacheType) {}
/*     */   
/*     */   private void readResources() {
/*  51 */     BufferedReader in = null;
/*     */     try {
/*  53 */       in = new BufferedReader(new FileReader("c:/tcg.resourcedebug.txt"));
/*     */       String line;
/*  55 */       while ((line = in.readLine()) != null) {
/*  56 */         if (!line.isEmpty()) {
/*  57 */           this.resources.add(line.split(":")[1]);
/*     */         }
/*     */       } 
/*  60 */     } catch (IOException e) {
/*  61 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/*  64 */         if (in != null) {
/*  65 */           in.close();
/*     */         }
/*  67 */       } catch (IOException ignore) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected <T> ManagedResource<T> getResourceOmnipowered(Class<T> resourceType, String name, ResourceListener<T> listener, CacheType cacheInMemory, Map<Object, Object> properties) throws ResourceManagerException {
/*  74 */     if (!this.initialized && DisplaySystem.getDisplaySystem().getRenderer() != null && GLContext.getCapabilities() != null) {
/*     */ 
/*     */       
/*  77 */       this.initialized = true;
/*  78 */       int size = this.resources.size();
/*  79 */       int count = 0;
/*  80 */       boolean oldHint = isMapLoadingHint();
/*  81 */       this.mapLoadingHint = true;
/*  82 */       for (String str : this.resources) {
/*  83 */         String[] parts = str.split("\\|");
/*  84 */         if (this.filter.canPreload(parts[0], parts[1])) {
/*     */           try {
/*  86 */             getResource(Class.forName(parts[0]), parts[1], CacheType.CACHE_PERMANENTLY);
/*  87 */             System.out.println("Loaded: " + count + "/" + size + " " + str);
/*  88 */           } catch (ClassNotFoundException e) {
/*  89 */             e.printStackTrace();
/*     */           } 
/*     */         }
/*  92 */         count++;
/*     */       } 
/*  94 */       this.mapLoadingHint = oldHint;
/*     */     } 
/*     */     
/*  97 */     long start = System.nanoTime();
/*  98 */     ManagedResource<T> resource = super.getResourceOmnipowered(resourceType, name, listener, cacheInMemory, properties);
/*  99 */     long used = System.nanoTime() - start;
/*     */     
/* 101 */     String key = resourceType.getName() + "|" + name;
/* 102 */     if (!this.resources.contains(key)) {
/* 103 */       this.resources.add(key);
/* 104 */       this.out.println(used + ":" + key);
/*     */     } 
/*     */     
/* 107 */     return resource;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\DebugResourceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */