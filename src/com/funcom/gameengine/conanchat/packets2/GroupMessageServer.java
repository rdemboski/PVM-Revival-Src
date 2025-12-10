/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Data;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer32;
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
/*    */ public class GroupMessageServer
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer40 groupid;
/*    */   private Integer32 clientid;
/*    */   private StringDatatype messageBody;
/*    */   private Data extraData;
/*    */   
/*    */   public GroupMessageServer() {}
/*    */   
/*    */   public GroupMessageServer(Integer40 groupid, Integer32 clientid, StringDatatype messageBody, Data extraData) {
/* 31 */     this.groupid = groupid;
/* 32 */     this.clientid = clientid;
/* 33 */     this.messageBody = messageBody;
/* 34 */     this.extraData = extraData;
/*    */   }
/*    */   
/*    */   public GroupMessageServer(ByteBuffer byteBuffer) {
/* 38 */     this.groupid = new Integer40(byteBuffer, Endianess.BIG_ENDIAN);
/* 39 */     this.clientid = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 40 */     this.messageBody = new StringDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/* 41 */     this.extraData = new Data(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 46 */     return 65;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 51 */     return new GroupMessageServer(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 56 */     return this.groupid.getSizeInBytes() + this.clientid.getSizeInBytes() + this.messageBody.getSizeInBytes() + this.extraData.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 64 */     this.groupid.toByteBuffer(buffer);
/* 65 */     this.clientid.toByteBuffer(buffer);
/* 66 */     this.messageBody.toByteBuffer(buffer);
/* 67 */     this.extraData.toByteBuffer(buffer);
/* 68 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer40 getGroupid() {
/* 72 */     return this.groupid;
/*    */   }
/*    */   
/*    */   public Integer32 getClientid() {
/* 76 */     return this.clientid;
/*    */   }
/*    */   
/*    */   public StringDatatype getMessageBody() {
/* 80 */     return this.messageBody;
/*    */   }
/*    */   
/*    */   public Data getExtraData() {
/* 84 */     return this.extraData;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\GroupMessageServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */