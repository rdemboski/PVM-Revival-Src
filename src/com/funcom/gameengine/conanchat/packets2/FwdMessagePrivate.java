/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Data;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer32;
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
/*    */ public class FwdMessagePrivate
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer32 toClientid;
/*    */   private StringDatatype messageBody;
/*    */   private Data extraData;
/*    */   
/*    */   public FwdMessagePrivate() {}
/*    */   
/*    */   public FwdMessagePrivate(Integer32 toClientid, StringDatatype messageBody, Data extraData) {
/* 29 */     this.toClientid = toClientid;
/* 30 */     this.messageBody = messageBody;
/* 31 */     this.extraData = extraData;
/*    */   }
/*    */   
/*    */   public FwdMessagePrivate(ByteBuffer byteBuffer) {
/* 35 */     this.toClientid = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 36 */     this.messageBody = new StringDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/* 37 */     this.extraData = new Data(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 42 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 47 */     return new FwdMessagePrivate(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 52 */     return this.toClientid.getSizeInBytes() + this.messageBody.getSizeInBytes() + this.extraData.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 59 */     this.toClientid.toByteBuffer(buffer);
/* 60 */     this.messageBody.toByteBuffer(buffer);
/* 61 */     this.extraData.toByteBuffer(buffer);
/* 62 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer32 getToClientid() {
/* 66 */     return this.toClientid;
/*    */   }
/*    */   
/*    */   public StringDatatype getMessageBody() {
/* 70 */     return this.messageBody;
/*    */   }
/*    */   
/*    */   public Data getExtraData() {
/* 74 */     return this.extraData;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\FwdMessagePrivate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */