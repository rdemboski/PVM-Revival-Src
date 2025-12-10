/*    */ package com.funcom.tcg.net;
/*    */ 
/*    */ import com.funcom.util.SizeCheckedArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DefaultSubscriptionState
/*    */   extends SubscriptionState {
/*  8 */   private List<ChangeListener> listeners = (List<ChangeListener>)new SizeCheckedArrayList(1, getClass().getSimpleName(), 4);
/*    */   private int flags;
/*    */   
/*    */   public DefaultSubscriptionState() {
/* 12 */     this(0);
/*    */   }
/*    */   
/*    */   public DefaultSubscriptionState(int flags) {
/* 16 */     setFlags(flags);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setFlags(int flags) {
/* 21 */     if (this.flags != flags) {
/* 22 */       this.flags = flags;
/*    */       
/* 24 */       for (ChangeListener listener : this.listeners) {
/* 25 */         listener.subscriptionStateChanged(this);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFlags() {
/* 32 */     return this.flags;
/*    */   }
/*    */   
/*    */   public void addListener(ChangeListener listener) {
/* 36 */     this.listeners.add(listener);
/*    */   }
/*    */   
/*    */   public void removeListener(ChangeListener listener) {
/* 40 */     this.listeners.remove(listener);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 45 */     if (this == o) {
/* 46 */       return true;
/*    */     }
/* 48 */     if (o == null || getClass() != o.getClass()) {
/* 49 */       return false;
/*    */     }
/*    */     
/* 52 */     DefaultSubscriptionState that = (DefaultSubscriptionState)o;
/*    */     
/* 54 */     if (this.flags != that.flags) {
/* 55 */       return false;
/*    */     }
/*    */     
/* 58 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 63 */     return this.flags;
/*    */   }
/*    */   
/*    */   public static interface ChangeListener {
/*    */     void subscriptionStateChanged(DefaultSubscriptionState param1DefaultSubscriptionState);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\DefaultSubscriptionState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */