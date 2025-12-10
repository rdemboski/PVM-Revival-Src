/*    */ package com.funcom.tcg.net;
/*    */ 
/*    */ public abstract class SubscriptionState {
/*    */   public static final int FLAG_APPROVED = 1;
/*    */   public static final int FLAG_UNAPPROVED = 2;
/*    */   public static final int FLAG_REGISTERED = 4;
/*    */   private static final int FLAG_SUBSCRIBER = 8;
/*    */   public static final int FLAG_DELETED = 16;
/*    */   public static final int FLAG_CANNED_CHAT = 32;
/*    */   
/*    */   public void set(SubscriptionState src) {
/* 12 */     setFlags(src.getFlags());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void setFlags(int paramInt);
/*    */ 
/*    */   
/*    */   public abstract int getFlags();
/*    */ 
/*    */   
/*    */   public boolean isRegistered() {
/* 24 */     return checkSubscriptionFlags(4);
/*    */   }
/*    */   
/*    */   public void setRegistered(boolean registered) {
/* 28 */     setSubscriptionFlags(registered, 4);
/*    */   }
/*    */   
/*    */   public void setSubscriber(boolean subscriber) {
/* 32 */     setSubscriptionFlags(subscriber, 8);
/*    */   }
/*    */   
/*    */   public boolean isSubscriber() {
/* 36 */     return checkSubscriptionFlags(8);
/*    */   }
/*    */   
/*    */   public void setDeleted(boolean deleted) {
/* 40 */     setSubscriptionFlags(deleted, 16);
/*    */   }
/*    */   
/*    */   public boolean isDeleted() {
/* 44 */     return checkSubscriptionFlags(16);
/*    */   }
/*    */   
/*    */   public void setCannedChat(boolean cannedChat) {
/* 48 */     setSubscriptionFlags(cannedChat, 32);
/*    */   }
/*    */   
/*    */   public boolean isCannedChat() {
/* 52 */     return checkSubscriptionFlags(32);
/*    */   }
/*    */   
/*    */   private boolean checkSubscriptionFlags(int flags) {
/* 56 */     return ((getFlags() & flags) == flags);
/*    */   }
/*    */   
/*    */   private void setSubscriptionFlags(boolean subscriber, int flags) {
/* 60 */     int _flags = getFlags();
/* 61 */     if (subscriber) {
/* 62 */       _flags |= flags;
/*    */     } else {
/* 64 */       _flags &= flags ^ 0xFFFFFFFF;
/*    */     } 
/* 66 */     setFlags(_flags);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 71 */     return "SubscriptionState{flags=" + Integer.toBinaryString(getFlags()) + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\SubscriptionState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */