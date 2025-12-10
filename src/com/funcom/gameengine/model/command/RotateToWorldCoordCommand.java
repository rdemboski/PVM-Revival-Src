/*    */ package com.funcom.gameengine.model.command;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ 
/*    */ public class RotateToWorldCoordCommand
/*    */   extends Command {
/*    */   private static final String COMMAND_NAME = "rotate";
/*    */   private boolean finished;
/*    */   private WorldCoordinate targetPos;
/*    */   
/*    */   public RotateToWorldCoordCommand(WorldCoordinate targetPos) {
/* 12 */     if (targetPos == null)
/* 13 */       throw new IllegalArgumentException("targetPos = null"); 
/* 14 */     this.targetPos = targetPos;
/* 15 */     this.finished = false;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 19 */     return "rotate";
/*    */   }
/*    */   
/*    */   public boolean isFinished() {
/* 23 */     return this.finished;
/*    */   }
/*    */   
/*    */   public void update(float time) {
/* 27 */     WorldCoordinate parentPos = getParentCreature().getPosition();
/* 28 */     double newAngle = parentPos.angleTo(this.targetPos);
/* 29 */     getParentCreature().setRotation((float)newAngle);
/* 30 */     this.finished = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\command\RotateToWorldCoordCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */