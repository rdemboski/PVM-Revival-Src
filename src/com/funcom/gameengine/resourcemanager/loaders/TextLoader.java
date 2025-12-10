/*    */ package com.funcom.gameengine.resourcemanager.loaders;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.LoadException;
/*    */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextLoader
/*    */   extends AbstractLoader
/*    */ {
/*    */   public TextLoader() {
/* 15 */     super(String.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/* 20 */     BufferedInputStream bis = null;
/*    */     try {
/* 22 */       InputStream is = getFileInputStream(managedResource.getName());
/* 23 */       bis = new BufferedInputStream(is);
/* 24 */       byte[] data = new byte[bis.available()];
/* 25 */       int read = bis.read(data);
/* 26 */       if (read != data.length) {
/* 27 */         throw new LoadException(getResourceManager(), managedResource, new RuntimeException("Data not read entirely, read bytes: " + read + ", should read: " + data.length));
/*    */       }
/*    */       
/* 30 */       String dataString = (new String(data)).trim();
/* 31 */       managedResource.setResource(dataString);
/* 32 */     } catch (IOException e) {
/* 33 */       throw new LoadException(getResourceManager(), managedResource, e);
/*    */     } finally {
/* 35 */       closeSafely(bis, managedResource);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\TextLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */