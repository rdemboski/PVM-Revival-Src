/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ import com.funcom.rpgengine2.abilities.movement.MovementManipulator;
/*    */ import java.util.Queue;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.LinkedBlockingQueue;
/*    */ 
/*    */ public class MovementManipulationSupport
/*    */   implements RpgQueryableSupport, RpgUpdateable
/*    */ {
/*    */   private final ImmunitySupport immunitySupport;
/*    */   private final MapSupport mapSupport;
/*    */   private boolean dead = false;
/* 14 */   private final Queue<MovementManipulator> movementManipulatorQueue = new LinkedBlockingQueue<MovementManipulator>();
/*    */   private volatile MovementManipulator movementManipulator;
/*    */   
/*    */   public MovementManipulationSupport(ImmunitySupport immunitySupport, MapSupport mapSupport) {
/* 18 */     this.immunitySupport = immunitySupport;
/* 19 */     this.mapSupport = mapSupport;
/*    */   }
/*    */   
/*    */   public void enqueueMovementManipulator(MovementManipulator movementManipulator) {
/* 23 */     if (!this.dead) {
/* 24 */       this.movementManipulatorQueue.add(movementManipulator);
/*    */     }
/*    */   }
/*    */   
/*    */   public int[] getUpdatePriorities() {
/* 29 */     return new int[] { 1 };
/*    */   }
/*    */   
/*    */   public void setDead(boolean dead) {
/* 33 */     if (dead) {
/* 34 */       clear();
/*    */     }
/* 36 */     this.dead = dead;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 40 */     this.movementManipulatorQueue.clear();
/* 41 */     this.movementManipulator = null;
/* 42 */     handleStop();
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(int priority, long updateMillis) {
/* 47 */     processIncomingManipulators();
/*    */     
/* 49 */     if (this.movementManipulator != null) {
/* 50 */       boolean finished = this.movementManipulator.update(updateMillis);
/* 51 */       if (finished) {
/* 52 */         handleStop();
/* 53 */         this.movementManipulator = null;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private void processIncomingManipulators() {
/* 59 */     while (!this.movementManipulatorQueue.isEmpty()) {
/* 60 */       MovementManipulator manipulator = this.movementManipulatorQueue.poll();
/* 61 */       Set<String> cureStatuses = manipulator.getParent().getCureStatuses();
/*    */       
/* 63 */       if (this.immunitySupport.isImmune(cureStatuses)) {
/* 64 */         this.immunitySupport.dispatchEvent(new ImmunitySupport.ImmuneEvent(manipulator.getSourceProvider())); continue;
/*    */       } 
/* 66 */       if (this.movementManipulator != null) {
/* 67 */         handleStop();
/*    */       }
/* 69 */       this.movementManipulator = manipulator;
/* 70 */       handleStart();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void handleStart() {
/* 76 */     this.mapSupport.setMovementOverride(true);
/*    */   }
/*    */   
/*    */   private void handleStop() {
/* 80 */     this.mapSupport.setMovementOverride(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\MovementManipulationSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */