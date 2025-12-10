/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Data;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*    */ import com.funcom.gameengine.conanchat.datatypes.StringDatatype;
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
/*    */ public class MessageAnonVicinity
/*    */   implements ChatMessage
/*    */ {
/*    */   private StringDatatype sendername;
/*    */   private StringDatatype messageBody;
/*    */   private Data extraData;
/*    */   
/*    */   public MessageAnonVicinity() {}
/*    */   
/*    */   public MessageAnonVicinity(StringDatatype sendername, StringDatatype messageBody, Data extraData) {
/* 28 */     this.sendername = sendername;
/* 29 */     this.messageBody = messageBody;
/* 30 */     this.extraData = extraData;
/*    */   }
/*    */   
/*    */   public MessageAnonVicinity(ByteBuffer byteBuffer) {
/* 34 */     this.sendername = new StringDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/* 35 */     this.messageBody = new StringDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/* 36 */     this.extraData = new Data(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 41 */     return 35;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 46 */     return new MessageAnonVicinity(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 51 */     return this.sendername.getSizeInBytes() + this.messageBody.getSizeInBytes() + this.extraData.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 58 */     this.sendername.toByteBuffer(buffer);
/* 59 */     this.messageBody.toByteBuffer(buffer);
/* 60 */     this.extraData.toByteBuffer(buffer);
/* 61 */     return buffer;
/*    */   }
/*    */   
/*    */   public StringDatatype getSendername() {
/* 65 */     return this.sendername;
/*    */   }
/*    */   
/*    */   public StringDatatype getMessageBody() {
/* 69 */     return this.messageBody;
/*    */   }
/*    */   
/*    */   public Data getExtraData() {
/* 73 */     return this.extraData;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\MessageAnonVicinity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */