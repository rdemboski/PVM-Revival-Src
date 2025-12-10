/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ import com.funcom.rpgengine2.Stat;
/*    */ import com.funcom.rpgengine2.abilities.AbstractDamageFilter;
/*    */ import com.funcom.rpgengine2.combat.Element;
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ import com.funcom.rpgengine2.creatures.StatSupport;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TCGDamageFilter
/*    */   extends AbstractDamageFilter
/*    */ {
/*    */   protected int getResistance(RpgEntity target, Element element) {
/* 15 */     Stat baseResistanceStat = ((StatSupport)target.getSupport(StatSupport.class)).getStatById((short)27);
/* 16 */     Stat resistanceStat = ((StatSupport)target.getSupport(StatSupport.class)).getStatById(StatId.getId(element).shortValue());
/*    */     
/* 18 */     if (baseResistanceStat == null) {
/* 19 */       throw new RuntimeException("Base Resistance stat not found: target=" + target + " statId=" + '\033');
/*    */     }
/* 21 */     if (resistanceStat == null) {
/* 22 */       throw new RuntimeException("Resistance stat not found: target=" + target + " element=" + element);
/*    */     }
/*    */     
/* 25 */     return resistanceStat.getSum() + baseResistanceStat.getSum();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\TCGDamageFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */