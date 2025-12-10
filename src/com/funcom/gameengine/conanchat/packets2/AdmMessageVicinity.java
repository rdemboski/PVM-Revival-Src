/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Data;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer32;
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
/*    */ public class AdmMessageVicinity
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer32 sernderClientid;
/*    */   private Integer32Array vectorOfRcptClientids;
/*    */   private StringDatatype messageBody;
/*    */   private Data extraData;
/*    */   
/*    */   public AdmMessageVicinity() {}
/*    */   
/*    */   public AdmMessageVicinity(Integer32 sernderClientid, Integer32Array vectorOfRcptClientids, StringDatatype messageBody, Data extraData) {
/* 31 */     this.sernderClientid = sernderClientid;
/* 32 */     this.vectorOfRcptClientids = vectorOfRcptClientids;
/* 33 */     this.messageBody = messageBody;
/* 34 */     this.extraData = extraData;
/*    */   }
/*    */   
/*    */   public AdmMessageVicinity(ByteBuffer byteBuffer) {
/* 38 */     this.sernderClientid = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 39 */     this.vectorOfRcptClientids = new Integer32Array(byteBuffer, Endianess.BIG_ENDIAN);
/* 40 */     this.messageBody = new StringDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/* 41 */     this.extraData = new Data(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 46 */     return 1010;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 51 */     return new AdmMessageVicinity(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 56 */     return this.sernderClientid.getSizeInBytes() + this.vectorOfRcptClientids.getSizeInBytes() + this.messageBody.getSizeInBytes() + this.extraData.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 64 */     this.sernderClientid.toByteBuffer(buffer);
/* 65 */     this.vectorOfRcptClientids.toByteBuffer(buffer);
/* 66 */     this.messageBody.toByteBuffer(buffer);
/* 67 */     this.extraData.toByteBuffer(buffer);
/* 68 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer32 getSernderClientid() {
/* 72 */     return this.sernderClientid;
/*    */   }
/*    */   
/*    */   public Integer32Array getVectorOfRcptClientids() {
/* 76 */     return this.vectorOfRcptClientids;
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


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\AdmMessageVicinity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */