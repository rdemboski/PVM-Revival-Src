/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AccessKeysMessage
/*    */   implements Message
/*    */ {
/*    */   private List<String> accessKeys;
/*    */   private List<Long> expireTimes;
/*    */   
/*    */   public AccessKeysMessage() {}
/*    */   
/*    */   public AccessKeysMessage(ByteBuffer buffer) {
/* 20 */     this.accessKeys = MessageUtils.readListStr(buffer);
/* 21 */     this.expireTimes = MessageUtils.readListLong(buffer);
/*    */   }
/*    */   
/*    */   public AccessKeysMessage(List<String> accessKeys, List<Long> expireTimes) {
/* 25 */     this.accessKeys = accessKeys;
/* 26 */     this.expireTimes = expireTimes;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 31 */     return 254;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 36 */     return new AccessKeysMessage(buffer);
/*    */   }
/*    */   
/*    */   public List<String> getAccessKeys() {
/* 40 */     return this.accessKeys;
/*    */   }
/*    */   
/*    */   public List<Long> getExpireTimes() {
/* 44 */     return this.expireTimes;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 49 */     return MessageUtils.getSizeListStr(this.accessKeys) + MessageUtils.getSizeListLong(this.expireTimes);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 54 */     MessageUtils.writeListStr(buffer, this.accessKeys);
/* 55 */     MessageUtils.writeListLong(buffer, this.expireTimes);
/* 56 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\AccessKeysMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */