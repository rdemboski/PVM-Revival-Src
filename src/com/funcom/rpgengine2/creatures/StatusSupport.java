/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ import com.funcom.rpgengine2.combat.RpgStatus;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StatusSupport
/*    */   implements RpgQueryableSupport, SupportEventListener
/*    */ {
/* 13 */   protected final Set<RpgStatus> rpgStatuses = new HashSet<RpgStatus>();
/*    */ 
/*    */   
/*    */   public boolean getStatus(RpgStatus status) {
/* 17 */     return this.rpgStatuses.contains(status);
/*    */   }
/*    */   
/*    */   public void addRpgStatus(RpgStatus rpgStatus) {
/* 21 */     this.rpgStatuses.add(rpgStatus);
/*    */   }
/*    */   
/*    */   public void removeRpgStatus(RpgStatus rpgStatus) {
/* 25 */     this.rpgStatuses.remove(rpgStatus);
/*    */   }
/*    */   
/*    */   public Set<RpgStatus> getRpgStatuses() {
/* 29 */     return this.rpgStatuses;
/*    */   }
/*    */   
/*    */   public void processEvent(SupportEvent event) {
/* 33 */     if (event.getType() == PassiveAbilityHandlingSupport.EventType.CLEAR_PASSIVE_ABILITIES)
/* 34 */       this.rpgStatuses.clear(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\StatusSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */