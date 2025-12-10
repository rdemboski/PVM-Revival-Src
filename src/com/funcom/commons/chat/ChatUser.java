/*    */ package com.funcom.commons.chat;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatUser
/*    */ {
/*    */   private int id;
/*    */   private String nick;
/*    */   
/*    */   public ChatUser() {}
/*    */   
/*    */   public ChatUser(int id, String nick) {
/* 17 */     this.id = id;
/* 18 */     this.nick = nick;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 22 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getNick() {
/* 26 */     return this.nick;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/* 30 */     if (this == o) return true; 
/* 31 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 33 */     ChatUser that = (ChatUser)o;
/* 34 */     return (this.id == that.id);
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 38 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 43 */     return "ChatUser{id=" + this.id + ", nick='" + this.nick + '\'' + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\ChatUser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */