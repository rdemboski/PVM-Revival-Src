/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.server.common.EncryptedByClientHint;
/*     */ import com.funcom.server.common.LoginRequestMessage;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import com.funcom.tcg.net.PlayerStartConfig;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ @EncryptedByClientHint
/*     */ public class LoginRequestMessage
/*     */   implements LoginRequestMessage
/*     */ {
/*  15 */   private String nick = "";
/*  16 */   private String password = "";
/*  17 */   private String autoLoginId = "";
/*     */   
/*     */   private int userId;
/*     */   
/*     */   private boolean allowRedirect;
/*     */   private PlayerStartConfig playerStartConfig;
/*     */   
/*     */   public LoginRequestMessage() {}
/*     */   
/*     */   public LoginRequestMessage(ByteBuffer buffer) {
/*  27 */     this.nick = MessageUtils.readStr(buffer);
/*  28 */     this.password = MessageUtils.readStr(buffer);
/*  29 */     this.autoLoginId = MessageUtils.readStr(buffer);
/*  30 */     this.userId = MessageUtils.readInt(buffer);
/*  31 */     this.allowRedirect = MessageUtils.readBoolean(buffer).booleanValue();
/*  32 */     this.playerStartConfig = TCGMessageUtils.readPlayerStartConfig(buffer);
/*     */   }
/*     */   
/*     */   public LoginRequestMessage(String nick, String password, boolean allowRedirect) {
/*  36 */     this(nick, password, null, allowRedirect);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LoginRequestMessage(String nick, String password, PlayerStartConfig playerStartConfig, boolean allowRedirect) {
/*  42 */     this.nick = nick;
/*  43 */     this.password = password;
/*  44 */     this.playerStartConfig = playerStartConfig;
/*  45 */     this.allowRedirect = allowRedirect;
/*     */   }
/*     */   
/*     */   public LoginRequestMessage(String nick, String password, String autoLoginId, int userId, boolean allowRedirect) {
/*  49 */     this.nick = nick;
/*  50 */     this.password = password;
/*  51 */     this.autoLoginId = autoLoginId;
/*  52 */     this.userId = userId;
/*  53 */     this.allowRedirect = allowRedirect;
/*     */   }
/*     */   
/*     */   public Message toMessage(ByteBuffer buffer) {
/*  57 */     return (Message)new LoginRequestMessage(buffer);
/*     */   }
/*     */   
/*     */   public String getNick() {
/*  61 */     return this.nick;
/*     */   }
/*     */   
/*     */   public String getPassword() {
/*  65 */     return this.password;
/*     */   }
/*     */   
/*     */   public String getAutoLoginId() {
/*  69 */     return this.autoLoginId;
/*     */   }
/*     */   
/*     */   public boolean isAutoLogin() {
/*  73 */     return (this.autoLoginId != null && !this.autoLoginId.isEmpty());
/*     */   }
/*     */   
/*     */   public int getUserId() {
/*  77 */     return this.userId;
/*     */   }
/*     */   
/*     */   public boolean allowRedirect() {
/*  81 */     return this.allowRedirect;
/*     */   }
/*     */   
/*     */   public PlayerStartConfig getPlayerStartConfig() {
/*  85 */     return this.playerStartConfig;
/*     */   }
/*     */   
/*     */   public short getMessageType() {
/*  89 */     return 5;
/*     */   }
/*     */   
/*     */   public int getSerializedSize() {
/*  93 */     return MessageUtils.getSizeStr(this.nick) + MessageUtils.getSizeStr(this.password) + MessageUtils.getSizeStr(this.autoLoginId) + MessageUtils.getSizeInt() + MessageUtils.getSizeBoolean() + TCGMessageUtils.getPlayerStartConfigSize(this.playerStartConfig);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer buffer) {
/*  99 */     MessageUtils.writeStr(buffer, this.nick);
/* 100 */     MessageUtils.writeStr(buffer, this.password);
/* 101 */     MessageUtils.writeStr(buffer, this.autoLoginId);
/* 102 */     MessageUtils.writeInt(buffer, this.userId);
/* 103 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.allowRedirect));
/* 104 */     TCGMessageUtils.writePlayerStartConfig(buffer, this.playerStartConfig);
/* 105 */     return buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 110 */     return "LoginRequestMessage{nick='" + this.nick + '\'' + ", password='" + this.password + '\'' + ", autoLoginId='" + this.autoLoginId + '\'' + ", userId=" + this.userId + ", playerStartConfig=" + this.playerStartConfig + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\LoginRequestMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */