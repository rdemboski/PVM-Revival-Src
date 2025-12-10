/*    */ package com.funcom.gameengine.ai;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DeadBrain
/*    */   extends AbstractBrain
/*    */ {
/*    */   public static final String BRAIN_NAME = "dead-brain";
/* 14 */   public static final DeadBrain INSTANCE = new DeadBrain();
/*    */   
/*    */   public String getName() {
/* 17 */     return "dead-brain";
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(float time) {
/* 22 */     if (getControlled().hasQueuedCommands())
/* 23 */       getControlled().clearQueue(); 
/* 24 */     if (getControlled().isDoingAnything())
/* 25 */       getControlled().stopCurrentWork(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\ai\DeadBrain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */