/*    */ package com.funcom.rpgengine2.creatures;
/*    */ import com.funcom.rpgengine2.abilities.SourceProvider;

import java.util.ArrayList;
/*    */ import java.util.Collections;
import java.util.List;
/*    */ import java.util.Queue;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.LinkedBlockingQueue;
/*    */ 
/*    */ public class ImmunitySupport implements RpgQueryableSupport, RpgUpdateable {
/*  9 */   private final List<String> immunities = Collections.synchronizedList(new ArrayList<String>());
/* 10 */   private final Queue<ImmuneEvent> events = new LinkedBlockingQueue<ImmuneEvent>();
/*    */   
/*    */   public boolean isImmune(String cureStatus) {
/* 13 */     return this.immunities.contains(cureStatus);
/*    */   }
/*    */   
/*    */   public void addImmunity(String immunity) {
/* 17 */     this.immunities.add(immunity);
/*    */   }
/*    */   
/*    */   public void removeImmunity(String immunity) {
/* 21 */     this.immunities.remove(immunity);
/*    */   }
/*    */   
/*    */   public boolean isImmune(Set<String> cureStatuses) {
/* 25 */     if (cureStatuses != null) {
/* 26 */       for (String cureStatus : cureStatuses) {
/* 27 */         if (isImmune(cureStatus)) {
/* 28 */           return true;
/*    */         }
/*    */       } 
/*    */     }
/* 32 */     return false;
/*    */   }
/*    */   
/*    */   public void dispatchEvent(ImmuneEvent immuneEvent) {
/* 36 */     this.events.add(immuneEvent);
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(int priority, long updateMillis) {
/* 41 */     while (!this.events.isEmpty()) {
/* 42 */       ImmuneEvent event = this.events.poll();
/* 43 */       onImmuneEvent(event);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onImmuneEvent(ImmuneEvent event) {}
/*    */ 
/*    */   
/*    */   public int[] getUpdatePriorities() {
/* 53 */     return NORMAL_SINGLE_UPDATE;
/*    */   }
/*    */   
/*    */   public static class ImmuneEvent {
/*    */     private final SourceProvider sourceProvider;
/*    */     
/*    */     public ImmuneEvent(SourceProvider sourceProvider) {
/* 60 */       this.sourceProvider = sourceProvider;
/*    */     }
/*    */     
/*    */     public SourceProvider getSourceProvider() {
/* 64 */       return this.sourceProvider;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\ImmunitySupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */