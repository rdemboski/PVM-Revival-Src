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
/*    */ public class CheckpointActivatedMessage
/*    */   implements Message
/*    */ {
/*    */   private int playerId;
/*    */   private String dfxText;
/*    */   
/*    */   public CheckpointActivatedMessage(int playerId, String dfxText) {
/* 19 */     this.playerId = playerId;
/* 20 */     this.dfxText = dfxText;
/*    */   }
/*    */   
/*    */   public CheckpointActivatedMessage(ByteBuffer buffer) {
/* 24 */     this.playerId = MessageUtils.readInt(buffer);
/* 25 */     this.dfxText = MessageUtils.readStr(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public CheckpointActivatedMessage() {}
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 33 */     return 50;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 38 */     return new CheckpointActivatedMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 43 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeStr(this.dfxText);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 48 */     MessageUtils.writeInt(buffer, this.playerId);
/* 49 */     MessageUtils.writeStr(buffer, this.dfxText);
/* 50 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getPlayerId() {
/* 54 */     return this.playerId;
/*    */   }
/*    */   
/*    */   public String getDfxText() {
/* 58 */     return this.dfxText;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 63 */     return "CheckpointActivatedMessage{playerId=" + this.playerId + ", dfxText='" + this.dfxText + '\'' + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\CheckpointActivatedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */