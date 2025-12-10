/*    */ package com.funcom.commons.jme;
/*    */ 
/*    */ import com.jme.image.Image;
/*    */ import java.awt.Dimension;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.MappedByteBuffer;
/*    */ import java.nio.channels.FileChannel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JmeJpegReader
/*    */ {
/*    */   public Image loadJpeg(File file) {
/* 42 */     FileInputStream fis = null;
/*    */     try {
/* 44 */       fis = new FileInputStream(file);
/* 45 */       FileChannel channel = fis.getChannel();
/*    */       
/* 47 */       MappedByteBuffer mappedBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
/* 48 */       Dimension size = findSize(mappedBuffer);
/* 49 */       ByteBuffer rgbaData = convertIntoRGBA(mappedBuffer);
/*    */       
/* 51 */       return new Image(Image.Format.RGBA8, size.width, size.height, rgbaData);
/* 52 */     } catch (FileNotFoundException e) {
/* 53 */       throw new IllegalStateException(e);
/* 54 */     } catch (IOException e) {
/* 55 */       throw new IllegalStateException(e);
/*    */     } finally {
/*    */       try {
/* 58 */         if (fis != null)
/* 59 */           fis.close(); 
/* 60 */       } catch (IOException e) {
/* 61 */         throw new IllegalStateException(e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private Dimension findSize(MappedByteBuffer mappedBuffer) {
/* 68 */     throw new IllegalStateException("WAITING TO BE IMPLEMENTED!");
/*    */   }
/*    */ 
/*    */   
/*    */   private ByteBuffer convertIntoRGBA(MappedByteBuffer mappedBuffer) {
/* 73 */     throw new IllegalStateException("WAITING TO BE IMPLEMENTED!");
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\JmeJpegReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */