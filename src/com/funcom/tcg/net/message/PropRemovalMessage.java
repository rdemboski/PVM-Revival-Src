/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.rpgengine2.creatures.RpgCreatureConstants;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PropRemovalMessage
/*    */   implements Message
/*    */ {
/*    */   private static final short TYPE_ID = 51;
/*    */   private int id;
/*    */   private RpgCreatureConstants.Type type;
/*    */   
/*    */   public PropRemovalMessage() {}
/*    */   
/*    */   public PropRemovalMessage(int id, RpgCreatureConstants.Type type) {
/* 23 */     this.id = id;
/* 24 */     this.type = type;
/*    */   }
/*    */   
/*    */   public PropRemovalMessage(ByteBuffer buffer) {
/* 28 */     this.id = MessageUtils.readInt(buffer);
/* 29 */     int typeInt = MessageUtils.readInt(buffer);
/* 30 */     this.type = RpgCreatureConstants.Type.valueById(typeInt);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 35 */     return 51;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 40 */     return new PropRemovalMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 45 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeInt();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 51 */     buffer.putInt(this.id);
/* 52 */     buffer.putInt(this.type.getTypeId());
/* 53 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 57 */     return this.id;
/*    */   }
/*    */   
/*    */   public RpgCreatureConstants.Type getType() {
/* 61 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\PropRemovalMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */