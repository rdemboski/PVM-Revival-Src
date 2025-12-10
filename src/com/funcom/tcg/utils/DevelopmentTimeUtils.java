/*    */ package com.funcom.tcg.utils;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import java.io.File;
/*    */ import java.io.FileNotFoundException;
/*    */ 
/*    */ 
/*    */ public class DevelopmentTimeUtils
/*    */ {
/*    */   public static void setupForExternalMaps(ResourceManager resourceManager, String mapPathStr) {
/* 11 */     File mapPath = (new File(mapPathStr)).getParentFile();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getPathName(String mapDirPath) {
/* 19 */     String mapDirName = mapDirPath.replace('\\', '/');
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 26 */     return mapDirName;
/*    */   }
/*    */   
/*    */   public static String getBootMapPath() {
/* 30 */     String mapProp = System.getProperty("tcg.bootmappath");
/*    */     
/* 32 */     if (mapProp != null && mapProp.length() > 0) {
/* 33 */       return mapProp;
/*    */     }
/*    */     
/* 36 */     return null;
/*    */   }
/*    */   
/*    */   public static String getFirstExistingFile(String fileList, String separationToken, String error) throws FileNotFoundException {
/* 40 */     String[] files = fileList.split(separationToken);
/*    */     
/* 42 */     for (String file : files) {
/* 43 */       File checkFile = new File(file);
/* 44 */       if (checkFile.exists()) {
/* 45 */         return file;
/*    */       }
/*    */     } 
/*    */     
/* 49 */     if (error != null) {
/* 50 */       throw new FileNotFoundException(error + " fileList=" + fileList + " workingDir=" + System.getProperty("user.dir"));
/*    */     }
/*    */     
/* 53 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tc\\utils\DevelopmentTimeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */