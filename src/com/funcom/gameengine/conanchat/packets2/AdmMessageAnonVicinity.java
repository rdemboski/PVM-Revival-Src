/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Data;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer32Array;
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
/*    */ public class AdmMessageAnonVicinity
/*    */   implements ChatMessage
/*    */ {
/*    */   private StringDatatype sernderName;
/*    */   private Integer32Array vectorOfRcptClientids;
/*    */   private StringDatatype messageBody;
/*    */   private Data extraData;
/*    */   
/*    */   public AdmMessageAnonVicinity() {}
/*    */   
/*    */   public AdmMessageAnonVicinity(StringDatatype sernderName, Integer32Array vectorOfRcptClientids, StringDatatype messageBody, Data extraData) {
/* 30 */     this.sernderName = sernderName;
/* 31 */     this.vectorOfRcptClientids = vectorOfRcptClientids;
/* 32 */     this.messageBody = messageBody;
/* 33 */     this.extraData = extraData;
/*    */   }
/*    */   
/*    */   public AdmMessageAnonVicinity(ByteBuffer byteBuffer) {
/* 37 */     this.sernderName = new StringDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/* 38 */     this.vectorOfRcptClientids = new Integer32Array(byteBuffer, Endianess.BIG_ENDIAN);
/* 39 */     this.messageBody = new StringDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/* 40 */     this.extraData = new Data(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 45 */     return 1011;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 50 */     return new AdmMessageAnonVicinity(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 55 */     return this.sernderName.getSizeInBytes() + this.vectorOfRcptClientids.getSizeInBytes() + this.messageBody.getSizeInBytes() + this.extraData.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 63 */     this.sernderName.toByteBuffer(buffer);
/* 64 */     this.vectorOfRcptClientids.toByteBuffer(buffer);
/* 65 */     this.messageBody.toByteBuffer(buffer);
/* 66 */     this.extraData.toByteBuffer(buffer);
/* 67 */     return buffer;
/*    */   }
/*    */   
/*    */   public StringDatatype getSernderName() {
/* 71 */     return this.sernderName;
/*    */   }
/*    */   
/*    */   public Integer32Array getVectorOfRcptClientids() {
/* 75 */     return this.vectorOfRcptClientids;
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


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\AdmMessageAnonVicinity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */