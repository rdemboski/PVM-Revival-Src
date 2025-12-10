/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UpdateTargetEffectMessage
/*    */   implements Message
/*    */ {
/*    */   private Integer id;
/*    */   private String dfxId;
/*    */   
/*    */   public UpdateTargetEffectMessage() {}
/*    */   
/*    */   public UpdateTargetEffectMessage(Integer id, String dfxId) {
/* 21 */     this.id = id;
/* 22 */     this.dfxId = dfxId;
/*    */   }
/*    */   
/*    */   public UpdateTargetEffectMessage(ByteBuffer buffer) {
/* 26 */     this.id = Integer.valueOf(MessageUtils.readInt(buffer));
/* 27 */     this.dfxId = MessageUtils.readStr(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 32 */     return 66;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 37 */     return new UpdateTargetEffectMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 42 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeStr(this.dfxId);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 47 */     MessageUtils.writeInt(buffer, this.id.intValue());
/* 48 */     MessageUtils.writeStr(buffer, this.dfxId);
/* 49 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer getId() {
/* 53 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getDfxId() {
/* 57 */     return this.dfxId;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\UpdateTargetEffectMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */