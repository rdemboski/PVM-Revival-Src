/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ import com.funcom.rpgengine2.items.Item;
/*    */ import com.funcom.rpgengine2.items.ItemType;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemUsageSupport
/*    */   implements RpgQueryableSupport
/*    */ {
/*    */   protected StatusSupport statusSupport;
/*    */   private long globalCooldownUntil;
/*    */   
/*    */   public ItemUsageSupport(StatusSupport statusSupport) {
/* 15 */     this.statusSupport = statusSupport;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean checkUseItemPermission(Item item, long currentTime) {
/* 25 */     ItemType itemType = item.getDescription().getItemType();
/*    */ 
/*    */     
/* 28 */     if (item.getDescription().getGlobalCooldown() != 0.0D && currentTime + item.getDescription().getGlobalCooldown() * 50.0D < this.globalCooldownUntil) {
/* 29 */       return false;
/*    */     }
/*    */     
/* 32 */     if (this.statusSupport != null && 
/* 33 */       this.statusSupport.getStatus(itemType.getBlockedBy())) {
/* 34 */       return false;
/*    */     }
/*    */     
/* 37 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void notifyItemUsed(Item item, RpgSourceProviderEntity usedByOwner, long currentTime) {
/* 50 */     double cooldown = item.getDescription().getGlobalCooldown();
/* 51 */     if (cooldown != 0.0D)
/* 52 */       this.globalCooldownUntil = currentTime + (long)(cooldown * 1000.0D); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\ItemUsageSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */