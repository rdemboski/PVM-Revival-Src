/*    */ package com.funcom.gameengine.resourcemanager;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.jar.JarFile;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JarOpener
/*    */ {
/*    */   private static JarOpener instance;
/* 13 */   private Map<String, JarFile> jars = new HashMap<String, JarFile>();
/*    */   
/*    */   public static JarOpener instance() {
/* 16 */     if (instance == null)
/* 17 */       instance = new JarOpener(); 
/* 18 */     return instance;
/*    */   }
/*    */   
/*    */   public JarFile getJarFile(String fileId) throws IOException {
/* 22 */     JarFile jar = this.jars.get(fileId);
/* 23 */     if (jar == null) {
/* 24 */       jar = new JarFile(fileId);
/* 25 */       this.jars.put(fileId, jar);
/*    */     } 
/* 27 */     return jar;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\JarOpener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */