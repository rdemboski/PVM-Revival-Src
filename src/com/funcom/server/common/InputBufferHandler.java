/*     */ package com.funcom.server.common;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InputBufferHandler
/*     */ {
/*  19 */   private static final Logger LOGGER = Logger.getLogger(InputBufferHandler.class);
/*     */   
/*     */   private InputBufferListener bufferListener;
/*     */   
/*     */   private ByteBuffer readBuffer;
/*     */   private IOBufferManager bufferManager;
/*     */   
/*     */   public InputBufferHandler(IOBufferManager bufferManager, InputBufferListener bufferListener) {
/*  27 */     this(bufferManager, bufferListener, 1024);
/*     */   }
/*     */   
/*     */   public InputBufferHandler(IOBufferManager bufferManager, InputBufferListener bufferListener, int readBufferSize) {
/*  31 */     this.bufferManager = bufferManager;
/*  32 */     this.bufferListener = bufferListener;
/*     */     
/*  34 */     this.readBuffer = bufferManager.getForRead(readBufferSize);
/*     */   }
/*     */   
/*     */   public InputBufferListener getBufferListener() {
/*  38 */     return this.bufferListener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer getReadBuffer() {
/*  47 */     return this.readBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process() throws MessageExceedsLimitException {
/*  58 */     int pos = this.readBuffer.position();
/*  59 */     if (pos >= 4) {
/*  60 */       int dataSize = this.readBuffer.getInt(0);
/*     */       
/*  62 */       if (dataSize < 0) {
/*  63 */         throw new MessageExceedsLimitException("bad data");
/*     */       }
/*     */ 
/*     */       
/*  67 */       if (dataSize > 25000)
/*     */       {
/*  69 */         LOGGER.warn("Message exceeds allowed limit size. likely inventory message");
/*     */       }
/*     */ 
/*     */       
/*  73 */       if (dataSize > this.readBuffer.capacity()) {
/*     */         
/*  75 */         ByteBuffer tmp = this.bufferManager.getForRead(dataSize);
/*  76 */         ByteBuffer orig = this.readBuffer;
/*  77 */         orig.flip();
/*  78 */         tmp.put(orig);
/*     */         
/*  80 */         this.bufferManager.putForReuse(orig);
/*  81 */         this.readBuffer = tmp;
/*     */       } 
/*     */ 
/*     */       
/*  85 */       int bufferSize = this.readBuffer.position();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  90 */       if (dataSize <= bufferSize) {
/*     */         
/*  92 */         int offset = 0;
/*  93 */         while (offset + dataSize <= bufferSize) {
/*     */ 
/*     */           
/*  96 */           ByteBuffer tmp = this.bufferManager.getForRead(dataSize);
/*  97 */           this.readBuffer.position(offset);
/*  98 */           this.readBuffer.limit(offset + dataSize);
/*  99 */           tmp.put(this.readBuffer);
/*     */           
/* 101 */           tmp.flip();
/* 102 */           tmp.position(4);
/*     */ 
/*     */           
/* 105 */           this.bufferListener.offer(tmp);
/*     */           
/* 107 */           offset += dataSize;
/*     */           
/* 109 */           if (bufferSize - offset >= 4) {
/*     */             
/* 111 */             this.readBuffer.limit(bufferSize);
/* 112 */             dataSize = this.readBuffer.getInt(offset);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 118 */         this.readBuffer.limit(bufferSize);
/*     */         
/* 120 */         this.readBuffer.compact();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\common\InputBufferHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */