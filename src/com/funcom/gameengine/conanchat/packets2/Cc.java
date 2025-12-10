/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer32;
/*    */ import com.funcom.gameengine.conanchat.datatypes.StringDatatypeArray;
/*    */ import com.funcom.server.common.Message;
/*    */ import java.nio.ByteBuffer;
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
/*    */ public class Cc
/*    */   implements ChatMessage
/*    */ {
/*    */   private StringDatatypeArray splittedCommandString;
/*    */   private Integer32 windowid;
/*    */   
/*    */   public Cc() {}
/*    */   
/*    */   public Cc(StringDatatypeArray splittedCommandString, Integer32 windowid) {
/* 27 */     this.splittedCommandString = splittedCommandString;
/* 28 */     this.windowid = windowid;
/*    */   }
/*    */   
/*    */   public Cc(ByteBuffer byteBuffer) {
/* 32 */     this.splittedCommandString = new StringDatatypeArray(byteBuffer, Endianess.BIG_ENDIAN);
/* 33 */     this.windowid = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 38 */     return 120;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 43 */     return new Cc(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 48 */     return this.splittedCommandString.getSizeInBytes() + this.windowid.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 54 */     this.splittedCommandString.toByteBuffer(buffer);
/* 55 */     this.windowid.toByteBuffer(buffer);
/* 56 */     return buffer;
/*    */   }
/*    */   
/*    */   public StringDatatypeArray getSplittedCommandString() {
/* 60 */     return this.splittedCommandString;
/*    */   }
/*    */   
/*    */   public Integer32 getWindowid() {
/* 64 */     return this.windowid;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\Cc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */