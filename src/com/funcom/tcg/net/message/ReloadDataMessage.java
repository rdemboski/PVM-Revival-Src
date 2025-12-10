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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReloadDataMessage
/*    */   implements Message
/*    */ {
/*    */   private boolean reloadCSV;
/*    */   private boolean reloadMapAssets;
/*    */   
/*    */   public ReloadDataMessage() {}
/*    */   
/*    */   public ReloadDataMessage(boolean reloadCSV, boolean reloadMapAssets) {
/* 25 */     this.reloadCSV = reloadCSV;
/* 26 */     this.reloadMapAssets = reloadMapAssets;
/*    */   }
/*    */   
/*    */   public ReloadDataMessage(ByteBuffer byteBuffer) {
/* 30 */     this.reloadCSV = MessageUtils.readBoolean(byteBuffer).booleanValue();
/* 31 */     this.reloadMapAssets = MessageUtils.readBoolean(byteBuffer).booleanValue();
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 35 */     return 26;
/*    */   }
/*    */   
/*    */   public ReloadDataMessage toMessage(ByteBuffer buffer) {
/* 39 */     return new ReloadDataMessage(buffer);
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 43 */     return MessageUtils.getSizeBoolean() * 2;
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 47 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.reloadCSV));
/* 48 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.reloadMapAssets));
/* 49 */     return buffer;
/*    */   }
/*    */   
/*    */   public boolean isReloadCSV() {
/* 53 */     return this.reloadCSV;
/*    */   }
/*    */   
/*    */   public boolean isReloadMapAssets() {
/* 57 */     return this.reloadMapAssets;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\ReloadDataMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */