/*    */ package com.funcom.gameengine.resourcemanager;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.StringReader;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Properties;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DependencyMapList
/*    */ {
/*    */   private static DependencyMapList INSTANCE;
/*    */   private List<String> mapList;
/*    */   private String downloaderPath;
/*    */   private String downloadToPath;
/*    */   
/*    */   public DependencyMapList(ResourceManager resourceManager) {
/* 20 */     Properties properties = new Properties();
/*    */     
/* 22 */     StringReader stringReader = new StringReader(resourceManager.<String>getResource(String.class, "configuration/dist/maps_for_dist.properties", CacheType.NOT_CACHED));
/*    */     
/*    */     try {
/* 25 */       properties.load(stringReader);
/* 26 */     } catch (IOException e) {
/* 27 */       throw new RuntimeException(e);
/*    */     } 
/* 29 */     String value = properties.getProperty("list.default");
/* 30 */     value = value.replace("xml", "");
/* 31 */     value = value.replace("/", "");
/* 32 */     value = value.replace("*", "");
/* 33 */     String[] maps = value.split(",");
/*    */     
/* 35 */     this.mapList = new ArrayList<String>();
/* 36 */     Collections.addAll(this.mapList, maps);
/* 37 */     this.mapList.remove(0);
/*    */     
/* 39 */     String downloaderPath = "downloader.path";
/* 40 */     String systemPath = System.getProperty(downloaderPath);
/* 41 */     if (systemPath == null) {
/* 42 */       this.downloaderPath = properties.getProperty(downloaderPath);
/*    */     } else {
/* 44 */       this.downloaderPath = systemPath;
/*    */     } 
/*    */     
/* 47 */     String downloadToPath = "download.to.path";
/* 48 */     String systemToPath = System.getProperty(downloadToPath);
/* 49 */     if (systemToPath == null) {
/* 50 */       this.downloadToPath = properties.getProperty(downloadToPath);
/*    */     } else {
/* 52 */       this.downloadToPath = systemToPath;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static DependencyMapList instance(ResourceManager resourceManager) {
/* 57 */     if (INSTANCE == null) {
/* 58 */       INSTANCE = new DependencyMapList(resourceManager);
/*    */     }
/* 60 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public List<String> getMapList() {
/* 64 */     return this.mapList;
/*    */   }
/*    */   
/*    */   public String getDownloaderPath() {
/* 68 */     return this.downloaderPath;
/*    */   }
/*    */   
/*    */   public String getDownloadToPath() {
/* 72 */     return this.downloadToPath;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\DependencyMapList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */