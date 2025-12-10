/*    */ package com.funcom.tcg.client.model.rpg;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class GiftBoxCollection {
/*  8 */   private final Map<Integer, ClientGiftBox> giftBoxes = new HashMap<Integer, ClientGiftBox>();
/*    */   private ChangeListener changeListener;
/*    */   
/*    */   public ClientGiftBox get(int giftBoxId) {
/* 12 */     return this.giftBoxes.get(Integer.valueOf(giftBoxId));
/*    */   }
/*    */   
/*    */   public void put(ClientGiftBox giftBox) {
/* 16 */     this.giftBoxes.put(giftBox.getId(), giftBox);
/* 17 */     fireChanged();
/*    */   }
/*    */   
/*    */   public void remove(int giftBoxId) {
/* 21 */     this.giftBoxes.remove(Integer.valueOf(giftBoxId));
/* 22 */     fireChanged();
/*    */   }
/*    */   
/*    */   private void fireChanged() {
/* 26 */     if (this.changeListener != null) {
/* 27 */       this.changeListener.contentChanged();
/*    */     }
/*    */   }
/*    */   
/*    */   public void setChangeListener(ChangeListener changeListener) {
/* 32 */     this.changeListener = changeListener;
/*    */   }
/*    */   
/*    */   public Collection<ClientGiftBox> getAll() {
/* 36 */     return this.giftBoxes.values();
/*    */   }
/*    */   
/*    */   public static interface ChangeListener {
/*    */     void contentChanged();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\GiftBoxCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */