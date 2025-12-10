/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DFXKillMessage
/*    */   implements Message
/*    */ {
/*    */   private int creatureId;
/*    */   private String dfxScript;
/*    */   private int creatureType;
/*    */   
/*    */   public DFXKillMessage() {}
/*    */   
/*    */   public DFXKillMessage(int creatureId, String dfxScript, int creatureType) {
/* 20 */     this.creatureId = creatureId;
/* 21 */     this.dfxScript = dfxScript;
/* 22 */     this.creatureType = creatureType;
/*    */   }
/*    */   
/*    */   public DFXKillMessage(ByteBuffer buffer) {
/* 26 */     this.creatureId = buffer.getInt();
/* 27 */     this.dfxScript = MessageUtils.readStr(buffer);
/* 28 */     this.creatureType = buffer.getInt();
/*    */   }
/*    */   
/*    */   public int getCreatureId() {
/* 32 */     return this.creatureId;
/*    */   }
/*    */   
/*    */   public String getDfxScript() {
/* 36 */     return this.dfxScript;
/*    */   }
/*    */   
/*    */   public int getCreatureType() {
/* 40 */     return this.creatureType;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 45 */     return 227;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 50 */     return new DFXKillMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 55 */     return MessageUtils.getSizeInt() * 2 + MessageUtils.getSizeStr(this.dfxScript);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 60 */     buffer.putInt(this.creatureId);
/* 61 */     MessageUtils.writeStr(buffer, this.dfxScript);
/* 62 */     buffer.putInt(this.creatureType);
/* 63 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\DFXKillMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */