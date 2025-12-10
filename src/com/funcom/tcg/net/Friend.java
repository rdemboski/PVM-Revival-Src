/*    */ package com.funcom.tcg.net;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Friend
/*    */ {
/*    */   String nickname;
/*    */   Boolean isBlocked;
/*    */   Boolean isOnline;
/*    */   
/*    */   public Friend(String nickname, Boolean blocked, Boolean online) {
/* 13 */     this.nickname = nickname;
/* 14 */     this.isBlocked = blocked;
/* 15 */     this.isOnline = online;
/*    */   }
/*    */   
/*    */   public String getNickname() {
/* 19 */     return this.nickname;
/*    */   }
/*    */   
/*    */   public void setNickname(String nickname) {
/* 23 */     this.nickname = nickname;
/*    */   }
/*    */   
/*    */   public Boolean isBlocked() {
/* 27 */     return this.isBlocked;
/*    */   }
/*    */   
/*    */   public void setBlocked(Boolean blocked) {
/* 31 */     this.isBlocked = blocked;
/*    */   }
/*    */   
/*    */   public Boolean isOnline() {
/* 35 */     return this.isOnline;
/*    */   }
/*    */   
/*    */   public void setOnline(Boolean online) {
/* 39 */     this.isOnline = online;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\Friend.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */