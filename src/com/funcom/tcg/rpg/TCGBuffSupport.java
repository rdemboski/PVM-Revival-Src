/*    */ package com.funcom.tcg.rpg;
/*    */ import com.funcom.rpgengine2.abilities.BuffType;
/*    */ import com.funcom.rpgengine2.buffs.Buff;
/*    */ import com.funcom.rpgengine2.creatures.ImmunitySupport;
/*    */ import com.funcom.rpgengine2.creatures.StatusSupport;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class TCGBuffSupport extends AbstractBuffSupport {
/* 11 */   private final Map<BuffType, List<Buff>> buffMapPerType = new HashMap<BuffType, List<Buff>>();
/*    */ 
/*    */   
/*    */   private final StatusSupport statusSupport;
/*    */ 
/*    */   
/*    */   private final ImmunitySupport immunitySupport;
/*    */ 
/*    */   
/*    */   public TCGBuffSupport(PassiveAbilityHandlingSupport passiveAbilityHandlingSupport, StatSupport statSupport, StatusSupport statusSupport, ImmunitySupport immunitySupport, RpgEntity owner) {
/* 21 */     super(passiveAbilityHandlingSupport, statSupport, owner);
/* 22 */     this.statusSupport = statusSupport;
/* 23 */     this.immunitySupport = immunitySupport;
/*    */   }
/*    */   
/*    */   protected List<Iterator<? extends Buff>> getBuffsIteratorList() {
/* 27 */     List<Iterator<? extends Buff>> iterList = new ArrayList<Iterator<? extends Buff>>(this.buffMapPerType.size());
/* 28 */     Set<BuffType> buffTypeSet = this.buffMapPerType.keySet();
/* 29 */     for (BuffType buffType : buffTypeSet) {
/* 30 */       iterList.add(((List<? extends Buff>)this.buffMapPerType.get(buffType)).iterator());
/*    */     }
/* 32 */     return iterList;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void processPendingBuff(Buff buff) {
/* 39 */     Set<String> cureStatuses = buff.getCreator().getCureStatuses();
/*    */     
/* 41 */     if (this.immunitySupport.isImmune(cureStatuses)) {
/* 42 */       this.immunitySupport.dispatchEvent(new ImmunitySupport.ImmuneEvent(buff.getBuffGiver()));
/*    */     } else {
/* 44 */       BuffType type = buff.getType();
/*    */       
/* 46 */       if (this.buffMapPerType.get(type) == null) {
/* 47 */         this.buffMapPerType.put(type, new ArrayList<Buff>());
/*    */       } else {
/* 49 */         List<Buff> list = this.buffMapPerType.get(type);
/* 50 */         for (int i = list.size() - 1; i > 0; i--) {
/* 51 */           Buff b = list.get(i);
/* 52 */           if (b.getCreator().equals(buff.getCreator())) {
/* 53 */             fireBuffRemoved(b);
/* 54 */             ((List)this.buffMapPerType.get(type)).remove(b);
/*    */           } 
/*    */         } 
/*    */       } 
/*    */       
/* 59 */       ((List<Buff>)this.buffMapPerType.get(type)).add(buff);
/* 60 */       fireBuffAdded(buff);
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<Buff> getBuffList(BuffType type) {
/* 65 */     return this.buffMapPerType.get(type);
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 69 */     for (BuffType buffType : this.buffMapPerType.keySet()) {
/* 70 */       if (getBuffList(buffType) != null && getBuffList(buffType).size() > 0) {
/* 71 */         return false;
/*    */       }
/*    */     } 
/* 74 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\TCGBuffSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */