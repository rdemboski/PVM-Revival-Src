/*    */ package com.funcom.gameengine.resourcemanager.loaders;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.LoadException;
/*    */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.ByteOrder;
/*    */ 
/*    */ public class BlobLoader
/*    */   extends AbstractLoader {
/*    */   public BlobLoader() {
/* 14 */     super(ByteBuffer.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/* 19 */     InputStream stream = null;
/*    */     try {
/* 21 */       stream = getFileInputStream(managedResource);
/* 22 */       ExposedByteArrayOutputStream bout = toByteArrayStream(stream);
/*    */       
/* 24 */       Boolean isDirect = (Boolean)managedResource.getParameter("direct", null);
/* 25 */       ByteBuffer blob = toBuffer(bout, isDirect);
/*    */       
/* 27 */       managedResource.setResource(blob);
/* 28 */     } catch (IOException e) {
/* 29 */       throw new LoadException(getResourceManager(), managedResource, e);
/*    */     } finally {
/* 31 */       closeSafely(stream, managedResource);
/*    */     } 
/*    */   }
/*    */   
/*    */   private ByteBuffer toBuffer(ExposedByteArrayOutputStream bout, Boolean isDirect) {
/*    */     ByteBuffer blob;
/* 37 */     if (isDirect != null && isDirect.booleanValue()) {
/* 38 */       blob = ByteBuffer.allocateDirect(bout.size());
/* 39 */       blob.order(ByteOrder.nativeOrder());
/*    */     } else {
/* 41 */       blob = ByteBuffer.allocate(bout.size());
/*    */     } 
/* 43 */     blob.put(bout.getInternalBuf(), 0, bout.size());
/* 44 */     blob.rewind();
/* 45 */     return blob;
/*    */   }
/*    */   
/*    */   private ExposedByteArrayOutputStream toByteArrayStream(InputStream stream) throws IOException {
/* 49 */     ExposedByteArrayOutputStream bout = new ExposedByteArrayOutputStream(stream.available());
/* 50 */     byte[] buf = new byte[10240];
/*    */     int readCount;
/* 52 */     while ((readCount = stream.read(buf)) >= 0) {
/* 53 */       bout.write(buf, 0, readCount);
/*    */     }
/* 55 */     stream.close();
/* 56 */     return bout;
/*    */   }
/*    */   
/*    */   private static class ExposedByteArrayOutputStream extends ByteArrayOutputStream {
/*    */     public ExposedByteArrayOutputStream(int size) {
/* 61 */       super(size);
/*    */     }
/*    */     
/*    */     public byte[] getInternalBuf() {
/* 65 */       return this.buf;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\BlobLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */