/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ import com.funcom.rpgengine2.StatSupportListener;
/*    */ import com.funcom.rpgengine2.abilities.StatModifier;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class StatModifierSupport
/*    */   implements RpgQueryableSupport, StatSupportListener, SupportEventListener
/*    */ {
/*    */   private List<StatModifier> statModifiers;
/*    */   
/*    */   public StatModifierSupport(StatSupport statSupport) {
/* 15 */     this.statModifiers = new ArrayList<StatModifier>();
/*    */     
/* 17 */     statSupport.addListener(this);
/*    */   }
/*    */   
/*    */   public void addUniqueStatModifier(StatModifier statModifier) {
/* 21 */     if (!this.statModifiers.contains(statModifier)) {
/* 22 */       this.statModifiers.add(statModifier);
/*    */     }
/*    */   }
/*    */   
/*    */   public void removeUniqueStatModifier(StatModifier statModifier) {
/* 27 */     this.statModifiers.remove(statModifier);
/*    */   }
/*    */   
/*    */   public void onResettedModifiers(StatSupport source, RpgEntity owner) {
/* 31 */     int size = this.statModifiers.size();
/* 32 */     for (int i = 0; i < size; i++) {
/* 33 */       StatModifier statModifier = this.statModifiers.get(i);
/*    */       
/* 35 */       statModifier.modifyStats(source.getStatCollection(), owner);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void processEvent(SupportEvent event) {
/* 40 */     if (event.getType() == PassiveAbilityHandlingSupport.EventType.CLEAR_PASSIVE_ABILITIES)
/* 41 */       this.statModifiers.clear(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\StatModifierSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */