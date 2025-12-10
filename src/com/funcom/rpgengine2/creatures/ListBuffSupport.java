/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ import com.funcom.rpgengine2.buffs.Buff;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ListBuffSupport
/*    */   extends AbstractBuffSupport
/*    */ {
/* 13 */   protected final List<Buff> buffs = new ArrayList<Buff>();
/*    */   
/*    */   public ListBuffSupport(PassiveAbilityHandlingSupport passiveAbilityHandlingSupport, StatSupport statSupport, RpgEntity owner) {
/* 16 */     super(passiveAbilityHandlingSupport, statSupport, owner);
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 20 */     return this.buffs.isEmpty();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void processPendingBuff(Buff buff) {
/* 26 */     this.buffs.add(buff);
/* 27 */     fireBuffAdded(buff);
/*    */   }
/*    */ 
/*    */   
/*    */   protected List<Iterator<? extends Buff>> getBuffsIteratorList() {
/* 32 */     List<Iterator<? extends Buff>> list = new ArrayList<Iterator<? extends Buff>>();
/* 33 */     list.add(this.buffs.iterator());
/* 34 */     return list;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\ListBuffSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */