/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.EncryptedByClientHint;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ @EncryptedByClientHint
/*    */ public class AccountRegisterRequestMessage
/*    */   implements Message
/*    */ {
/*    */   private volatile String name;
/*    */   private volatile String plainPassword;
/*    */   private volatile String email;
/*    */   private volatile String locale;
/*    */   private volatile String gamecode;
/*    */   
/*    */   public AccountRegisterRequestMessage() {}
/*    */   
/*    */   public AccountRegisterRequestMessage(String name, String plainPassword, String email, String locale, String gamecode) {
/* 22 */     this.name = name;
/* 23 */     this.plainPassword = plainPassword;
/* 24 */     this.email = email;
/* 25 */     this.locale = locale;
/* 26 */     this.gamecode = gamecode;
/*    */   }
/*    */   
/*    */   public AccountRegisterRequestMessage(ByteBuffer buffer) {
/* 30 */     this.name = MessageUtils.readStr(buffer);
/* 31 */     this.plainPassword = MessageUtils.readStr(buffer);
/* 32 */     this.email = MessageUtils.readStr(buffer);
/* 33 */     this.locale = MessageUtils.readStr(buffer);
/* 34 */     this.gamecode = MessageUtils.readStr(buffer);
/*    */   }
/*    */   
/*    */   public String getName() {
/* 38 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getPlainPassword() {
/* 42 */     return this.plainPassword;
/*    */   }
/*    */   
/*    */   public String getEmail() {
/* 46 */     return this.email;
/*    */   }
/*    */   
/*    */   public String getGamecode() {
/* 50 */     return this.gamecode;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 55 */     return 14;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 60 */     return new AccountRegisterRequestMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 65 */     return MessageUtils.getSizeStr(this.name) + MessageUtils.getSizeStr(this.plainPassword) + MessageUtils.getSizeStr(this.email) + MessageUtils.getSizeStr(this.locale) + MessageUtils.getSizeStr(this.gamecode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 74 */     MessageUtils.writeStr(buffer, this.name);
/* 75 */     MessageUtils.writeStr(buffer, this.plainPassword);
/* 76 */     MessageUtils.writeStr(buffer, this.email);
/* 77 */     MessageUtils.writeStr(buffer, this.locale);
/* 78 */     MessageUtils.writeStr(buffer, this.gamecode);
/*    */     
/* 80 */     return buffer;
/*    */   }
/*    */   
/*    */   public String getLocale() {
/* 84 */     return this.locale;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\AccountRegisterRequestMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */