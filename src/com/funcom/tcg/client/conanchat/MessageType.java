/*    */ package com.funcom.tcg.client.conanchat;
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum MessageType
/*    */ {
/*  7 */   info("#system_info"),
/*  8 */   warning("#System_warning"),
/*  9 */   error("#System_error"),
/* 10 */   whisper("#whisper"),
/* 11 */   moderator("#moderator");
/*    */   
/*    */   private String nickname;
/*    */   
/*    */   MessageType(String nickname) {
/* 16 */     this.nickname = nickname;
/*    */   }
/*    */   
/*    */   public String getNickname() {
/* 20 */     return this.nickname;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\conanchat\MessageType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */