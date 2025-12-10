/*    */ package com.funcom.gameengine.conanchat.datatypes;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringDatatypeArray
/*    */   extends AbstractDatatype
/*    */ {
/*    */   private StringDatatype[] strings;
/*    */   
/*    */   public StringDatatypeArray() {}
/*    */   
/*    */   public StringDatatypeArray(StringDatatype[] strings, Endianess endianess) {
/* 16 */     setEndianess(endianess);
/* 17 */     this.strings = new StringDatatype[strings.length];
/* 18 */     System.arraycopy(strings, 0, this.strings, 0, strings.length);
/* 19 */     for (StringDatatype stringDatatype : strings)
/* 20 */       stringDatatype.setEndianess(endianess); 
/*    */   }
/*    */   
/*    */   public StringDatatypeArray(ByteBuffer byteBuffer, Endianess endianess) {
/* 24 */     setEndianess(endianess);
/* 25 */     readValue(byteBuffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer toByteBuffer(ByteBuffer byteBuffer) {
/* 30 */     Integer16 length = new Integer16(this.strings.length, getEndianess());
/* 31 */     length.toByteBuffer(byteBuffer);
/*    */     
/* 33 */     for (StringDatatype sd : this.strings)
/* 34 */       sd.toByteBuffer(byteBuffer); 
/* 35 */     return byteBuffer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readValue(ByteBuffer byteBuffer) {
/* 40 */     Integer16 length = new Integer16(byteBuffer, getEndianess());
/*    */     
/* 42 */     this.strings = new StringDatatype[length.getIntValue()];
/* 43 */     for (int i = 0; i < this.strings.length; i++) {
/* 44 */       this.strings[i] = new StringDatatype(byteBuffer, getEndianess());
/*    */     }
/*    */   }
/*    */   
/*    */   public int getSizeInBytes() {
/* 49 */     int arraySize = 0;
/* 50 */     for (StringDatatype sd : this.strings)
/* 51 */       arraySize += sd.getSizeInBytes(); 
/* 52 */     return 2 + arraySize;
/*    */   }
/*    */   
/*    */   public StringDatatype[] getStrings() {
/* 56 */     return this.strings;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\datatypes\StringDatatypeArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */