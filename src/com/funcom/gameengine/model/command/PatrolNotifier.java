/*    */ package com.funcom.gameengine.model.command;
/*    */ 
/*    */ import com.funcom.gameengine.ai.BrainControlled;
/*    */ import com.funcom.gameengine.ai.patrol.PatrolNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface PatrolNotifier
/*    */ {
/* 14 */   public static final PatrolNotifier NULL_OBJECT = new PatrolNotifier() {
/*    */       public void patrolPointReached(BrainControlled brainControlled, PatrolNode patrolNode) {}
/*    */     };
/*    */   
/*    */   void patrolPointReached(BrainControlled paramBrainControlled, PatrolNode paramPatrolNode);
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\command\PatrolNotifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */