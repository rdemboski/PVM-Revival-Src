/*     */ package com.funcom.server.common;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import java.util.zip.DataFormatException;
/*     */ import java.util.zip.Deflater;
/*     */ import java.util.zip.Inflater;
/*     */ 
/*     */ 
/*     */ public class CompressedBaseMessageFactory
/*     */   extends BaseMessageFactory
/*     */ {
/*     */   private static final int HEADER_BYTES = 9;
/*     */   
/*     */   public CompressedBaseMessageFactory(IOBufferManager bufferManager, int bufferSize, List<Class<? extends Message>> messageClasses) {
/*  16 */     super(bufferManager, bufferSize, messageClasses);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ByteBuffer getWriteBuffer(int size) {
/*  22 */     ByteBuffer buffer = super.getWriteBuffer(9 + size);
/*     */     
/*  24 */     buffer.put((byte)0);
/*  25 */     buffer.putInt(0);
/*  26 */     buffer.putInt(0);
/*     */     
/*  28 */     return buffer;
/*     */   }
/*     */   
/*     */   public ByteBuffer toBuffer(Message message) {
/*  32 */     ByteBuffer ret = super.toBuffer(message);
/*     */ 
/*     */ 
/*     */     
/*  36 */     if (ret.limit() > 100) {
/*  37 */       this.bufferManager.moveToWriteData(ret);
/*  38 */       ret.position(ret.position() + 9);
/*  39 */       byte[] data = new byte[ret.remaining()];
/*  40 */       ret.get(data);
/*  41 */       ret.rewind();
/*     */ 
/*     */       
/*  44 */       byte[] output = new byte[data.length * 3];
/*  45 */       Deflater compresser = new Deflater(1, false);
/*  46 */       compresser.setInput(data);
/*  47 */       compresser.finish();
/*  48 */       int compLen = compresser.deflate(output);
/*  49 */       compresser.end();
/*     */       
/*  51 */       if (compLen < data.length - 8) {
/*     */ 
/*     */         
/*  54 */         this.bufferManager.moveToWriteData(ret);
/*     */         
/*  56 */         ret.put((byte)1);
/*  57 */         ret.putInt(compLen);
/*  58 */         ret.putInt(data.length);
/*  59 */         ret.put(output, 0, compLen);
/*     */         
/*  61 */         this.bufferManager.prepareForWrite(ret, false);
/*     */       } 
/*     */     } 
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
/*     */ 
/*     */     
/*  78 */     return ret;
/*     */   }
/*     */   
/*     */   public Message toMessage(ByteBuffer buffer) throws UnknownMessageTypeException {
/*  82 */     byte compressedFlag = buffer.get();
/*  83 */     int compLen = buffer.getInt();
/*  84 */     int rawLen = buffer.getInt();
/*     */     
/*  86 */     if (compressedFlag != 0) {
/*     */ 
/*     */       
/*  89 */       Inflater decompresser = new Inflater();
/*     */       try {
/*  91 */         decompresser.setInput(buffer.array(), buffer.position(), compLen);
/*  92 */         byte[] result = new byte[rawLen];
/*  93 */         int resultLength = decompresser.inflate(result);
/*     */         
/*  95 */         if (buffer.capacity() < resultLength) {
/*  96 */           buffer = this.bufferManager.getForRead(resultLength);
/*     */         }
/*     */         
/*  99 */         buffer.position(0);
/* 100 */         buffer.put(result, 0, resultLength);
/* 101 */         buffer.flip();
/* 102 */       } catch (DataFormatException e) {
/* 103 */         throw new RuntimeException(e);
/*     */       } finally {
/* 105 */         decompresser.end();
/*     */       } 
/*     */     } 
/*     */     
/* 109 */     Message message = super.toMessage(buffer);
/* 110 */     return message;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\common\CompressedBaseMessageFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */