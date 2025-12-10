/*    */ package com.funcom.gameengine.sshshell;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ public enum SSHCommandType
/*    */ {
/*  8 */   REFRESH_PLAYER("refresh", (short)1),
/*  9 */   SERVER_NOTICE("message", (short)2),
/* 10 */   QUIT("quit", (short)3),
/* 11 */   COMPLETE_REFRESH_PLAYER("completerefresh", (short)4),
/* 12 */   SAVE_REQUEST("save", (short)5);
/*    */   
/*    */   public final String commandId;
/*    */   public final short messageId;
/*    */   
/*    */   SSHCommandType(String commandId, short messageId) {
/* 18 */     this.commandId = commandId;
/* 19 */     this.messageId = messageId;
/*    */   }
/*    */   
/*    */   public static SSHCommandType valueForId(String messageType) {
/* 23 */     for (SSHCommandType sshMessageType : values()) {
/* 24 */       if (sshMessageType.commandId.equals(messageType))
/* 25 */         return sshMessageType; 
/*    */     } 
/* 27 */     return null;
/*    */   }
/*    */   
/*    */   public static String[] createCommand(ByteBuffer buffer) {
/* 31 */     char[] transferBuffer = new char[buffer.limit()];
/* 32 */     for (int i = 0; i < transferBuffer.length; i++) {
/* 33 */       transferBuffer[i] = (char)buffer.get();
/*    */     }
/* 35 */     String commandWithParams = new String(transferBuffer);
/* 36 */     return commandWithParams.split("( |\n)");
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\sshshell\SSHCommandType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */