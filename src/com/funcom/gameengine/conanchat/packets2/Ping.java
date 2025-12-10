/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Data;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*    */ import com.funcom.server.common.Message;
/*    */ import java.nio.BufferUnderflowException;
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
/*    */ public class Ping
/*    */   implements ChatMessage
/*    */ {
/*    */   private Data canBeUsedForAnything;
/*    */   
/*    */   public Ping() {}
/*    */   
/*    */   public Ping(Data canBeUsedForAnything) {
/* 25 */     this.canBeUsedForAnything = canBeUsedForAnything;
/*    */   }
/*    */   
/*    */   public Ping(ByteBuffer byteBuffer) {
/*    */     try {
/* 30 */       this.canBeUsedForAnything = new Data(byteBuffer, Endianess.BIG_ENDIAN);
/* 31 */     } catch (BufferUnderflowException e) {}
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 40 */     return 100;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 45 */     return new Ping(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 50 */     return this.canBeUsedForAnything.getSizeInBytes();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 55 */     this.canBeUsedForAnything.toByteBuffer(buffer);
/* 56 */     return buffer;
/*    */   }
/*    */   
/*    */   public Data getCanBeUsedForAnything() {
/* 60 */     return this.canBeUsedForAnything;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\Ping.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */