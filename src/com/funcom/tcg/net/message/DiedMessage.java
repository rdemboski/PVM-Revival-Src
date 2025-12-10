/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.rpgengine2.combat.Element;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DiedMessage
/*    */   implements Message
/*    */ {
/*    */   private Element killedByElement;
/*    */   private String impact;
/*    */   
/*    */   public DiedMessage() {}
/*    */   
/*    */   public DiedMessage(Element killedByElement, String impact) {
/* 26 */     this.killedByElement = killedByElement;
/* 27 */     this.impact = impact;
/*    */   }
/*    */   
/*    */   public DiedMessage(ByteBuffer byteBuffer) {
/* 31 */     this.killedByElement = Element.values()[MessageUtils.readInt(byteBuffer)];
/* 32 */     this.impact = MessageUtils.readStr(byteBuffer);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 36 */     return 207;
/*    */   }
/*    */   
/*    */   public DiedMessage toMessage(ByteBuffer buffer) {
/* 40 */     return new DiedMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 44 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeStr(this.impact);
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 48 */     MessageUtils.writeInt(buffer, this.killedByElement.ordinal());
/* 49 */     MessageUtils.writeStr(buffer, this.impact);
/* 50 */     return buffer;
/*    */   }
/*    */   
/*    */   public Element getKilledByElement() {
/* 54 */     return this.killedByElement;
/*    */   }
/*    */   
/*    */   public String getImpact() {
/* 58 */     return this.impact;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\DiedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */