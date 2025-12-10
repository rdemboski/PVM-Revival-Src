/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class TCGGiftBoxManager {
/*  8 */   private Map<String, GiftBoxDescription> descriptions = new HashMap<String, GiftBoxDescription>();
/*    */   
/*    */   public GiftBoxDescription getDescription(String giftBoxId) {
/* 11 */     return this.descriptions.get(giftBoxId);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 15 */     this.descriptions.clear();
/*    */   }
/*    */   
/*    */   public void addDescription(GiftBoxDescription description) {
/* 19 */     this.descriptions.put(description.getId(), description);
/*    */   }
/*    */   
/*    */   public Collection<GiftBoxDescription> getDescriptions() {
/* 23 */     return this.descriptions.values();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\TCGGiftBoxManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */