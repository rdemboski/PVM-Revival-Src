/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class UpdateClientTownportalMessage
/*    */   implements Message
/*    */ {
/*    */   private boolean usable;
/*    */   
/*    */   public UpdateClientTownportalMessage() {}
/*    */   
/*    */   public UpdateClientTownportalMessage(boolean usable) {
/* 15 */     this.usable = usable;
/*    */   }
/*    */   
/*    */   public UpdateClientTownportalMessage(ByteBuffer buffer) {
/* 19 */     this.usable = MessageUtils.readBoolean(buffer).booleanValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 24 */     return 52;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 29 */     return new UpdateClientTownportalMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 34 */     return MessageUtils.getSizeBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 39 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.usable));
/* 40 */     return buffer;
/*    */   }
/*    */   
/*    */   public boolean isUsable() {
/* 44 */     return this.usable;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 49 */     return "UpdateClientTownportalMessage{usable=" + this.usable + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\UpdateClientTownportalMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */