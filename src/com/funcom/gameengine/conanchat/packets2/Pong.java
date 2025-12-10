/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Data;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
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
/*    */ public class Pong
/*    */   implements ChatMessage
/*    */ {
/*    */   private Data canBeUsedForAnything;
/*    */   
/*    */   public Pong() {}
/*    */   
/*    */   public Pong(Data canBeUsedForAnything) {
/* 25 */     this.canBeUsedForAnything = canBeUsedForAnything;
/*    */   }
/*    */   
/*    */   public Pong(ByteBuffer byteBuffer) {
/* 29 */     this.canBeUsedForAnything = new Data(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 34 */     return 100;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 39 */     return new Pong(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 44 */     return this.canBeUsedForAnything.getSizeInBytes();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 49 */     this.canBeUsedForAnything.toByteBuffer(buffer);
/* 50 */     return buffer;
/*    */   }
/*    */   
/*    */   public Data getCanBeUsedForAnything() {
/* 54 */     return this.canBeUsedForAnything;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\Pong.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */