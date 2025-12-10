/*    */ package com.funcom.server.common;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LogoutMessage
/*    */   implements Message
/*    */ {
/*    */   private boolean loggedInFromAnotherClient;
/*    */   private boolean serverShuttingDown;
/*    */   
/*    */   public LogoutMessage() {}
/*    */   
/*    */   public LogoutMessage(boolean loggedInFromAnotherClient, boolean serverShuttingDown) {
/* 17 */     this.loggedInFromAnotherClient = loggedInFromAnotherClient;
/* 18 */     this.serverShuttingDown = serverShuttingDown;
/*    */   }
/*    */   
/*    */   public LogoutMessage(ByteBuffer buffer) {
/* 22 */     this.loggedInFromAnotherClient = MessageUtils.readBoolean(buffer).booleanValue();
/* 23 */     this.serverShuttingDown = MessageUtils.readBoolean(buffer).booleanValue();
/*    */   }
/*    */   
/*    */   public boolean isLoggedInFromAnotherClient() {
/* 27 */     return this.loggedInFromAnotherClient;
/*    */   }
/*    */   
/*    */   public boolean isServerShuttingDown() {
/* 31 */     return this.serverShuttingDown;
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 35 */     return new LogoutMessage(buffer);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 39 */     return -2;
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 43 */     return MessageUtils.getSizeBoolean() * 2;
/*    */   }
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 47 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.loggedInFromAnotherClient));
/* 48 */     MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.serverShuttingDown));
/* 49 */     return buffer;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 54 */     return "LogoutMessage{loggedInFromAnotherClient=" + this.loggedInFromAnotherClient + ", serverShuttingDown=" + this.serverShuttingDown + '}';
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LocalGameClient.DisconnectReason getReason() {
/* 62 */     if (this.loggedInFromAnotherClient) {
/* 63 */       return LocalGameClient.DisconnectReason.LOGGED_IN_FROM_ANOTHER_CLIENT;
/*    */     }
/*    */     
/* 66 */     if (this.serverShuttingDown) {
/* 67 */       return LocalGameClient.DisconnectReason.SERVER_SHUTDOWN;
/*    */     }
/*    */     
/* 70 */     return LocalGameClient.DisconnectReason.CLIENT_QUIT;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\common\LogoutMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */