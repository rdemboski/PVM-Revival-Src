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
/*    */ public class DFXExecuteMessage
/*    */   implements Message
/*    */ {
/*    */   private int creatureId;
/*    */   private String dfxScript;
/*    */   private float localTime;
/*    */   
/*    */   public DFXExecuteMessage() {}
/*    */   
/*    */   public DFXExecuteMessage(int creatureId, String dfxScript, float localTime) {
/* 21 */     this.creatureId = creatureId;
/* 22 */     this.dfxScript = dfxScript;
/* 23 */     this.localTime = localTime;
/*    */   }
/*    */   
/*    */   public DFXExecuteMessage(ByteBuffer buffer) {
/* 27 */     this.creatureId = buffer.getInt();
/* 28 */     this.dfxScript = MessageUtils.readStr(buffer);
/* 29 */     this.localTime = buffer.getFloat();
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 33 */     return 225;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 37 */     return new DFXExecuteMessage(buffer);
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 41 */     buffer.putInt(this.creatureId);
/* 42 */     MessageUtils.writeStr(buffer, this.dfxScript);
/* 43 */     buffer.putFloat(this.localTime);
/*    */     
/* 45 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 49 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeStr(this.dfxScript) + MessageUtils.getSizeFloat();
/*    */   }
/*    */   
/*    */   public int getCreatureId() {
/* 53 */     return this.creatureId;
/*    */   }
/*    */   
/*    */   public String getDfxScript() {
/* 57 */     return this.dfxScript;
/*    */   }
/*    */   
/*    */   public float getLocalTime() {
/* 61 */     return this.localTime;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\DFXExecuteMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */