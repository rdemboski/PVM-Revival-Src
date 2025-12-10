/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Data;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer40;
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
/*    */ public class GroupAnonMessage
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer40 groupid;
/*    */   private StringDatatype sendername;
/*    */   private StringDatatype messageBody;
/*    */   private Data extraData;
/*    */   
/*    */   public GroupAnonMessage() {}
/*    */   
/*    */   public GroupAnonMessage(Integer40 groupid, StringDatatype sendername, StringDatatype messageBody, Data extraData) {
/* 30 */     this.groupid = groupid;
/* 31 */     this.sendername = sendername;
/* 32 */     this.messageBody = messageBody;
/* 33 */     this.extraData = extraData;
/*    */   }
/*    */   
/*    */   public GroupAnonMessage(ByteBuffer byteBuffer) {
/* 37 */     this.groupid = new Integer40(byteBuffer, Endianess.BIG_ENDIAN);
/* 38 */     this.sendername = new StringDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/* 39 */     this.messageBody = new StringDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/* 40 */     this.extraData = new Data(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 45 */     return 67;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 50 */     return new GroupAnonMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 55 */     return this.groupid.getSizeInBytes() + this.sendername.getSizeInBytes() + this.messageBody.getSizeInBytes() + this.extraData.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 63 */     this.groupid.toByteBuffer(buffer);
/* 64 */     this.sendername.toByteBuffer(buffer);
/* 65 */     this.messageBody.toByteBuffer(buffer);
/* 66 */     this.extraData.toByteBuffer(buffer);
/* 67 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer40 getGroupid() {
/* 71 */     return this.groupid;
/*    */   }
/*    */   
/*    */   public StringDatatype getSendername() {
/* 75 */     return this.sendername;
/*    */   }
/*    */   
/*    */   public StringDatatype getMessageBody() {
/* 79 */     return this.messageBody;
/*    */   }
/*    */   
/*    */   public Data getExtraData() {
/* 83 */     return this.extraData;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\GroupAnonMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */