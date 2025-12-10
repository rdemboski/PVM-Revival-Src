/*    */ package com.funcom.commons.chat;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public final class UserService
/*    */ {
/* 13 */   private static final UserService INSTANCE = new UserService();
/*    */   
/*    */   public static UserService instance() {
/* 16 */     return INSTANCE;
/*    */   }
/*    */   private Set<ChatUser> chatUsers;
/*    */   public static void registerMockUsers() {
/* 20 */     INSTANCE.chatUsers.add(new ChatUser(0, "Alice"));
/* 21 */     INSTANCE.chatUsers.add(new ChatUser(1, "QueenOfHearts"));
/* 22 */     INSTANCE.chatUsers.add(new ChatUser(2, "WhiteRabbit"));
/* 23 */     INSTANCE.chatUsers.add(new ChatUser(3, "CheshireCat"));
/* 24 */     INSTANCE.chatUsers.add(new ChatUser(4, "GnomeElder"));
/* 25 */     INSTANCE.chatUsers.add(new ChatUser(5, "MockTurtle"));
/* 26 */     INSTANCE.chatUsers.add(new ChatUser(6, "BillMcGill"));
/* 27 */     INSTANCE.chatUsers.add(new ChatUser(7, "Caterpillar"));
/* 28 */     INSTANCE.chatUsers.add(new ChatUser(8, "MarchHare"));
/* 29 */     INSTANCE.chatUsers.add(new ChatUser(9, "Dormouse"));
/* 30 */     INSTANCE.chatUsers.add(new ChatUser(10, "Gryphon"));
/* 31 */     INSTANCE.chatUsers.add(new ChatUser(11, "WhiteKing"));
/* 32 */     INSTANCE.chatUsers.add(new ChatUser(12, "WhiteQueen"));
/* 33 */     INSTANCE.chatUsers.add(new ChatUser(13, "HumptyDumpty"));
/* 34 */     INSTANCE.chatUsers.add(new ChatUser(14, "HieronymousQWilson"));
/* 35 */     INSTANCE.chatUsers.add(new ChatUser(15, "TheDuchess"));
/* 36 */     INSTANCE.chatUsers.add(new ChatUser(16, "VoraciousCentipede"));
/* 37 */     INSTANCE.chatUsers.add(new ChatUser(17, "RedKing"));
/* 38 */     INSTANCE.chatUsers.add(new ChatUser(18, "TweedleDee"));
/* 39 */     INSTANCE.chatUsers.add(new ChatUser(19, "TweedleDum"));
/* 40 */     INSTANCE.chatUsers.add(new ChatUser(20, "MadHatter"));
/* 41 */     INSTANCE.chatUsers.add(new ChatUser(21, "Jabberwock"));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private UserService() {
/* 47 */     this.chatUsers = new HashSet<ChatUser>();
/*    */   }
/*    */   
/*    */   public ChatUser findById(int id) {
/* 51 */     for (ChatUser chatUser : this.chatUsers) {
/* 52 */       if (chatUser.getId() == id)
/* 53 */         return chatUser; 
/* 54 */     }  return null;
/*    */   }
/*    */   
/*    */   public ChatUser findByNick(String nick) {
/* 58 */     for (ChatUser chatUser : this.chatUsers) {
/* 59 */       if (chatUser.getNick().equals(nick))
/* 60 */         return chatUser; 
/* 61 */     }  return null;
/*    */   }
/*    */   
/*    */   public int count() {
/* 65 */     return this.chatUsers.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\UserService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */