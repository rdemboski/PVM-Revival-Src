/*    */ package com.funcom.gameengine.model.command;
/*    */ 
/*    */ public class IdleCommand
/*    */   extends Command {
/*    */   public static final String COMMAND_NAME = "idle";
/*    */   
/*    */   public String getName() {
/*  8 */     return "idle";
/*    */   }
/*    */   
/*    */   public boolean isFinished() {
/* 12 */     return getParentCreature().hasQueuedCommands();
/*    */   }
/*    */   
/*    */   public void update(float time) {}
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\command\IdleCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */