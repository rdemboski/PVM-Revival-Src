/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CastTimeFeedbackMessage
/*    */   implements Message
/*    */ {
/* 13 */   public static final Logger LOGGER = Logger.getLogger(CastTimeFeedbackMessage.class);
/*    */   
/*    */   private static final short TYPE_ID = 68;
/*    */   
/*    */   private String id;
/*    */   private int tier;
/*    */   private Type type;
/*    */   
/*    */   public CastTimeFeedbackMessage() {}
/*    */   
/*    */   public CastTimeFeedbackMessage(ByteBuffer buffer) {
/* 24 */     this.id = MessageUtils.readStr(buffer);
/* 25 */     this.tier = MessageUtils.readInt(buffer);
/* 26 */     this.type = Type.values()[MessageUtils.readInt(buffer)];
/*    */   }
/*    */   
/*    */   public CastTimeFeedbackMessage(String id, int tier, Type type) {
/* 30 */     this.id = id;
/* 31 */     this.tier = tier;
/* 32 */     this.type = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 37 */     return 68;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 42 */     return new CastTimeFeedbackMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 47 */     return MessageUtils.getSizeStr(this.id) + MessageUtils.getSizeInt() + MessageUtils.getSizeInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 52 */     MessageUtils.writeStr(buffer, this.id);
/* 53 */     MessageUtils.writeInt(buffer, this.tier);
/* 54 */     MessageUtils.writeInt(buffer, this.type.ordinal());
/* 55 */     return buffer;
/*    */   }
/*    */   
/*    */   public enum Type
/*    */   {
/* 60 */     END_CASTTIME_SUCCESS, FAILURE;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 64 */     return this.id;
/*    */   }
/*    */   
/*    */   public int getTier() {
/* 68 */     return this.tier;
/*    */   }
/*    */   
/*    */   public Type getType() {
/* 72 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\CastTimeFeedbackMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */