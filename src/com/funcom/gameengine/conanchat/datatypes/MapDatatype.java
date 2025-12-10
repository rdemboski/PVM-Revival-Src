/*    */ package com.funcom.gameengine.conanchat.datatypes;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.AbstractMap;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapDatatype
/*    */   extends AbstractDatatype
/*    */ {
/* 18 */   private List<Map.Entry<StringDatatype, Data>> entryList = new LinkedList<Map.Entry<StringDatatype, Data>>();
/*    */ 
/*    */   
/*    */   public MapDatatype() {}
/*    */   
/*    */   public MapDatatype(List<Map.Entry<StringDatatype, Data>> entryList) {
/* 24 */     this.entryList.clear();
/* 25 */     this.entryList.addAll(entryList);
/*    */   }
/*    */   
/*    */   public MapDatatype(ByteBuffer byteBuffer, Endianess endianess) {
/* 29 */     setEndianess(endianess);
/* 30 */     readValue(byteBuffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer toByteBuffer(ByteBuffer byteBuffer) {
/* 35 */     switch (getEndianess()) {
/*    */       case BIG_ENDIAN:
/* 37 */         writeBigEndian(byteBuffer);
/*    */         break;
/*    */       case LITTLE_ENDIAN:
/* 40 */         writeLittleEndian(byteBuffer);
/*    */         break;
/*    */     } 
/* 43 */     return byteBuffer;
/*    */   }
/*    */   
/*    */   private void writeLittleEndian(ByteBuffer byteBuffer) {
/* 47 */     throw new IllegalStateException("Shouldn't ever be called!");
/*    */   }
/*    */   
/*    */   private void writeBigEndian(ByteBuffer byteBuffer) {
/* 51 */     byteBuffer.put((byte)this.entryList.size());
/* 52 */     for (Map.Entry<StringDatatype, Data> entry : this.entryList) {
/* 53 */       ((StringDatatype)entry.getKey()).toByteBuffer(byteBuffer);
/* 54 */       ((Data)entry.getValue()).toByteBuffer(byteBuffer);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void readValue(ByteBuffer byteBuffer) {
/* 60 */     switch (getEndianess()) {
/*    */       case BIG_ENDIAN:
/* 62 */         readBigEndian(byteBuffer);
/*    */         break;
/*    */       case LITTLE_ENDIAN:
/* 65 */         readLittleEndian(byteBuffer);
/*    */         break;
/*    */     } 
/*    */   }
/*    */   
/*    */   private void readLittleEndian(ByteBuffer byteBuffer) {
/* 71 */     throw new IllegalStateException("Shouldn't ever be called!");
/*    */   }
/*    */   
/*    */   private void readBigEndian(ByteBuffer byteBuffer) {
/* 75 */     int size = byteBuffer.get();
/* 76 */     for (int i = 0; i < size; i++) {
/* 77 */       StringDatatype st = new StringDatatype(byteBuffer);
/* 78 */       Data data = new Data(byteBuffer, Endianess.BIG_ENDIAN);
/* 79 */       this.entryList.add(new AbstractMap.SimpleEntry<StringDatatype, Data>(st, data));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSizeInBytes() {
/* 85 */     int size = 1;
/* 86 */     for (Map.Entry<StringDatatype, Data> entry : this.entryList) {
/* 87 */       size += ((StringDatatype)entry.getKey()).getSizeInBytes();
/* 88 */       size += ((Data)entry.getValue()).getSizeInBytes();
/*    */     } 
/* 90 */     return size;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEndianess(Endianess endianess) {
/* 95 */     if (endianess.equals(Endianess.LITTLE_ENDIAN)) {
/* 96 */       throw new IllegalStateException("This class can only be serialized in one way!");
/*    */     }
/* 98 */     super.setEndianess(endianess);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\datatypes\MapDatatype.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */