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
/*    */ public class ActivateLoadingScreenMessage
/*    */   implements Message
/*    */ {
/*    */   private String mapId;
/*    */   private boolean loadDefault;
/*    */   
/*    */   public ActivateLoadingScreenMessage(String mapId, boolean loadDefault) {
/* 19 */     this.mapId = mapId;
/* 20 */     this.loadDefault = loadDefault;
/*    */   }
/*    */ 
/*    */   
/*    */   public ActivateLoadingScreenMessage() {}
/*    */   
/*    */   public ActivateLoadingScreenMessage(ByteBuffer buffer) {
/* 27 */     this.mapId = MessageUtils.readStr(buffer);
/* 28 */     this.loadDefault = MessageUtils.readBoolean(buffer).booleanValue();
/*    */   }
/*    */   
/*    */   public String getMapId() {
/* 32 */     return this.mapId;
/*    */   }
/*    */   
/*    */   public boolean isLoadDefault() {
/* 36 */     return this.loadDefault;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 41 */     return 49;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 46 */     return new ActivateLoadingScreenMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 51 */     return MessageUtils.getSizeStr(this.mapId) + MessageUtils.getSizeBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 56 */     MessageUtils.writeStr(buffer, this.mapId);
/* 57 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.loadDefault));
/* 58 */     return buffer;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 63 */     return "ActivateLoadingScreenMessage{mapId='" + this.mapId + '\'' + ", loadDefault=" + this.loadDefault + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\ActivateLoadingScreenMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */