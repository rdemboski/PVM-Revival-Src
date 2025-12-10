/*    */ package com.funcom.gameengine.conanchat.datatypes;
/*    */ 
/*    */ import com.funcom.server.common.MessageIOFatalException;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringDatatype
/*    */   extends AbstractDatatype
/*    */ {
/*    */   private String string;
/*    */   
/*    */   public StringDatatype() {}
/*    */   
/*    */   public StringDatatype(String string) {
/* 21 */     this.string = string;
/*    */   }
/*    */   
/*    */   public StringDatatype(String string, Endianess endianess) {
/* 25 */     this.string = string;
/* 26 */     setEndianess(endianess);
/*    */   }
/*    */   
/*    */   public StringDatatype(ByteBuffer byteBuffer) {
/* 30 */     readValue(byteBuffer);
/*    */   }
/*    */   
/*    */   public StringDatatype(ByteBuffer byteBuffer, Endianess endianess) {
/* 34 */     setEndianess(endianess);
/* 35 */     readValue(byteBuffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer toByteBuffer(ByteBuffer byteBuffer) {
/* 40 */     if (this.string.length() == 0) {
/* 41 */       (new Integer16(1, getEndianess())).toByteBuffer(byteBuffer);
/* 42 */       (new Integer8(0)).toByteBuffer(byteBuffer);
/*    */     } else {
/* 44 */       byte[] bytes = getStringBytes();
/* 45 */       Integer16 size = getSizeBytes(bytes);
/* 46 */       size.toByteBuffer(byteBuffer);
/* 47 */       byteBuffer.put(bytes);
/*    */     } 
/* 49 */     return byteBuffer;
/*    */   }
/*    */   
/*    */   private Integer16 getSizeBytes(byte[] bytes) {
/*    */     try {
/* 54 */       return new Integer16(bytes.length, getEndianess());
/* 55 */     } catch (IllegalArgumentException e) {
/* 56 */       throw new MessageIOFatalException("String data is too long, length=" + bytes.length, e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private byte[] getStringBytes() {
/*    */     try {
/* 62 */       return this.string.getBytes("UTF-8");
/* 63 */     } catch (UnsupportedEncodingException e) {
/* 64 */       throw new MessageIOFatalException("Problem with UTF-8, server want UTF-8. String not added to buffer", e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void readValue(ByteBuffer byteBuffer) {
/* 70 */     int length = (new Integer16(byteBuffer, getEndianess())).getIntValue();
/* 71 */     byte[] strData = new byte[length];
/* 72 */     byteBuffer.get(strData);
/*    */     try {
/* 74 */       this.string = new String(strData, "UTF-8");
/* 75 */     } catch (UnsupportedEncodingException e) {
/* 76 */       throw new MessageIOFatalException("Problem with UTF-8, server sends UTF-8. Message cannot be read from buffer", e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSizeInBytes() {
/*    */     try {
/* 83 */       return (new Integer16()).getSizeInBytes() + (this.string.getBytes("UTF-8")).length;
/* 84 */     } catch (UnsupportedEncodingException e) {
/* 85 */       throw new IllegalStateException("UTF-8 is not supported, not able to get size in bytes", e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getValue() {
/* 90 */     return this.string;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\datatypes\StringDatatype.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */