/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ public class ServerStatesResponseMessage
/*    */   implements Message
/*    */ {
/*    */   private boolean characterCreateEnabled;
/*    */   private String startMap;
/*    */   
/*    */   public ServerStatesResponseMessage() {}
/*    */   
/*    */   public ServerStatesResponseMessage(boolean characterCreateEnabled, String startMap) {
/* 17 */     this.characterCreateEnabled = characterCreateEnabled;
/* 18 */     this.startMap = startMap;
/*    */   }
/*    */   
/*    */   public ServerStatesResponseMessage(ByteBuffer buffer) {
/* 22 */     this.characterCreateEnabled = MessageUtils.readBoolean(buffer).booleanValue();
/* 23 */     this.startMap = MessageUtils.readStr(buffer);
/*    */   }
/*    */   
/*    */   public boolean isCharacterCreateEnabled() {
/* 27 */     return this.characterCreateEnabled;
/*    */   }
/*    */   
/*    */   public String getStartMap() {
/* 31 */     return this.startMap;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 36 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 41 */     return new ServerStatesResponseMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 46 */     return MessageUtils.getSizeBoolean() + MessageUtils.getSizeStr(this.startMap);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 51 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.characterCreateEnabled));
/* 52 */     MessageUtils.writeStr(buffer, this.startMap);
/* 53 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\ServerStatesResponseMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */