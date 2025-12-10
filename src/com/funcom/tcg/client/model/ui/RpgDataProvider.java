/*    */ package com.funcom.tcg.client.model.ui;
/*    */ 
/*    */ import com.funcom.rpgengine2.monsters.MonsterHardness;
/*    */ import com.funcom.tcg.net.message.StateUpdateMessage;
/*    */ 
/*    */ public interface RpgDataProvider
/*    */ {
/*    */   void addListener(DataListener paramDataListener);
/*    */   
/*    */   void removeListener(DataListener paramDataListener);
/*    */   
/*    */   String getName();
/*    */   
/*    */   int getLevel();
/*    */   
/*    */   float getHealthFraction();
/*    */   
/*    */   RpgType getRpgType();
/*    */   
/*    */   StateUpdateMessage.BuffData getBuff();
/*    */   
/*    */   boolean rpgTypeChanged();
/*    */   
/*    */   boolean nameChanged();
/*    */   
/*    */   boolean levelChanged();
/*    */   
/*    */   boolean healthFractionChanged();
/*    */   
/*    */   public static interface DataListener
/*    */   {
/*    */     void dataChanged(boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3);
/*    */   }
/*    */   
/*    */   public enum RpgType {
/* 36 */     PLAYER,
/* 37 */     MOB,
/* 38 */     MINIBOSS,
/* 39 */     BOSS;
/*    */     
/*    */     static RpgType convertFromHardness(MonsterHardness monsterHardness) {
/* 42 */       switch (monsterHardness) {
/*    */         case BOSS:
/* 44 */           return BOSS;
/*    */         case MINI_BOSS:
/* 46 */           return MINIBOSS;
/*    */         case MOB:
/* 48 */           return MOB;
/*    */         case TRASH:
/* 50 */           return MOB;
/*    */         case NOLOOT:
/* 52 */           return MOB;
/*    */       } 
/* 54 */       throw new IllegalStateException("Unknown monster hardness");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\mode\\ui\RpgDataProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */