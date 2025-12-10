/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import com.funcom.tcg.portals.InteractibleType;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InteractiblePropActionInvokedMessage
/*    */   implements Message
/*    */ {
/*    */   private int playerId;
/*    */   private String interactiblePropId;
/*    */   private InteractibleType interactibleType;
/*    */   
/*    */   public InteractiblePropActionInvokedMessage() {}
/*    */   
/*    */   public InteractiblePropActionInvokedMessage(ByteBuffer buffer) {
/* 25 */     this.playerId = MessageUtils.readInt(buffer);
/* 26 */     this.interactiblePropId = MessageUtils.readStr(buffer);
/* 27 */     byte typeId = buffer.get();
/* 28 */     this.interactibleType = InteractibleType.valueById(typeId);
/*    */   }
/*    */   
/*    */   public InteractiblePropActionInvokedMessage(int playerId, String interactiblePropId, InteractibleType interactibleType) {
/* 32 */     this.playerId = playerId;
/* 33 */     this.interactiblePropId = interactiblePropId;
/* 34 */     this.interactibleType = interactibleType;
/*    */   }
/*    */   
/*    */   public int getPlayerId() {
/* 38 */     return this.playerId;
/*    */   }
/*    */   
/*    */   public String getInteractiblePropId() {
/* 42 */     return this.interactiblePropId;
/*    */   }
/*    */   
/*    */   public InteractibleType getPortalType() {
/* 46 */     return this.interactibleType;
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 50 */     return 32;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 54 */     return new InteractiblePropActionInvokedMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 58 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeStr(this.interactiblePropId) + MessageUtils.getSizeFloat();
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 62 */     MessageUtils.writeInt(buffer, this.playerId);
/* 63 */     MessageUtils.writeStr(buffer, this.interactiblePropId);
/* 64 */     buffer.put(this.interactibleType.id);
/* 65 */     return buffer;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 71 */     return "InteractiblePropActionInvokedMessage{playerId=" + this.playerId + ", interactiblePropId='" + this.interactiblePropId + '\'' + ", interactibleType=" + this.interactibleType + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\InteractiblePropActionInvokedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */