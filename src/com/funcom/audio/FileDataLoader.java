/*    */ package com.funcom.audio;
/*    */ 
/*    */ import com.funcom.audio.fmod.MemoryFileSystem;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.ByteBuffer;
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.Priority;
/*    */ import org.jouvieje.FmodEx.Misc.BufferUtils;
/*    */ 
/*    */ public class FileDataLoader implements DataLoader {
/* 17 */   private static final Logger LOGGER = Logger.getLogger(FileDataLoader.class.getName());
/*    */   
/*    */   private final String baseDir;
/*    */   
/*    */   public FileDataLoader(String baseDir) {
/* 22 */     if (baseDir == null) {
/* 23 */       baseDir = "";
/*    */     }
/* 25 */     if (!baseDir.endsWith("\\") && !baseDir.endsWith("/"))
/*    */     {
/* 27 */       baseDir = baseDir + "/";
/*    */     }
/* 29 */     this.baseDir = baseDir;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer load(String path) {
/* 34 */     InputStream is = null;
/*    */     try {
/* 36 */       is = MemoryFileSystem.class.getResourceAsStream(this.baseDir + path);
/* 37 */       if (is == null) {
/* 38 */         File file = new File(this.baseDir + path);
/* 39 */         if (file.exists()) {
/* 40 */           is = new FileInputStream(file);
/*    */         }
/*    */       } 
/* 43 */       BufferedInputStream bis = new BufferedInputStream(is);
/* 44 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*    */       
/* 46 */       byte[] bytes = new byte[4096];
/*    */       int read;
/* 48 */       while ((read = bis.read(bytes, 0, bytes.length)) != -1) {
/* 49 */         baos.write(bytes, 0, read);
/*    */       }
/*    */       
/* 52 */       ByteBuffer buffer = BufferUtils.newByteBuffer(baos.size());
/* 53 */       buffer.put(baos.toByteArray());
/* 54 */       buffer.rewind();
/* 55 */       LOGGER.log((Priority)Level.INFO, "SIZE: " + buffer.limit());
/*    */       
/* 57 */       return buffer;
/* 58 */     } catch (IOException e) {
/* 59 */       return null;
/*    */     } finally {
/* 61 */       if (is != null)
/*    */         try {
/* 63 */           is.close();
/* 64 */         } catch (IOException ignore) {} 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\audio\FileDataLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */