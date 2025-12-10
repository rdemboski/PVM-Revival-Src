/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Data;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer32;
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
/*    */ public class MessageSystemLocal
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer32 clientid;
/*    */   private Integer32 windowid;
/*    */   private Integer32 messageHash;
/*    */   private Data argsToTheFormatString;
/*    */   
/*    */   public MessageSystemLocal() {}
/*    */   
/*    */   public MessageSystemLocal(Integer32 clientid, Integer32 windowid, Integer32 messageHash, Data argsToTheFormatString) {
/* 29 */     this.clientid = clientid;
/* 30 */     this.windowid = windowid;
/* 31 */     this.messageHash = messageHash;
/* 32 */     this.argsToTheFormatString = argsToTheFormatString;
/*    */   }
/*    */   
/*    */   public MessageSystemLocal(ByteBuffer byteBuffer) {
/* 36 */     this.clientid = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 37 */     this.windowid = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 38 */     this.messageHash = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 39 */     this.argsToTheFormatString = new Data(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 44 */     return 37;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 49 */     return new MessageSystemLocal(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 54 */     return this.clientid.getSizeInBytes() + this.windowid.getSizeInBytes() + this.messageHash.getSizeInBytes() + this.argsToTheFormatString.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 62 */     this.clientid.toByteBuffer(buffer);
/* 63 */     this.windowid.toByteBuffer(buffer);
/* 64 */     this.messageHash.toByteBuffer(buffer);
/* 65 */     this.argsToTheFormatString.toByteBuffer(buffer);
/* 66 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer32 getClientid() {
/* 70 */     return this.clientid;
/*    */   }
/*    */   
/*    */   public Integer32 getWindowid() {
/* 74 */     return this.windowid;
/*    */   }
/*    */   
/*    */   public Integer32 getMessageHash() {
/* 78 */     return this.messageHash;
/*    */   }
/*    */   
/*    */   public Data getArgsToTheFormatString() {
/* 82 */     return this.argsToTheFormatString;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\MessageSystemLocal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */