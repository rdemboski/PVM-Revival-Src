/*    */ package com.funcom.gameengine.model.command;
/*    */ 
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ 
/*    */ public class InteractCommand
/*    */   extends Command {
/*    */   public static final String COMMAND_NAME = "interact";
/*    */   private InteractibleProp target;
/*    */   private Creature player;
/*    */   private boolean interacted;
/*    */   private String actionName;
/*    */   
/*    */   public InteractCommand(InteractibleProp target, Creature player, String actionName) {
/* 15 */     this.target = target;
/* 16 */     this.player = player;
/* 17 */     this.actionName = actionName;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 21 */     return "interact";
/*    */   }
/*    */   
/*    */   public boolean isFinished() {
/* 25 */     return this.interacted;
/*    */   }
/*    */   
/*    */   public void update(float time) {
/* 29 */     if (!this.interacted) {
/* 30 */       this.interacted = true;
/* 31 */       if (this.target.hasAction(this.actionName))
/* 32 */         this.target.invokeAction(this.actionName, (InteractibleProp)this.player, ""); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\command\InteractCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */