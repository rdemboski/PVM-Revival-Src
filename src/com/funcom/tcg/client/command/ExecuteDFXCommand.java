/*    */ package com.funcom.tcg.client.command;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffect;
/*    */ import com.funcom.gameengine.model.command.Command;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExecuteDFXCommand
/*    */   extends Command
/*    */ {
/*    */   private static final String COMMAND_NAME = "use-item";
/*    */   protected final PropNode propNode;
/*    */   protected final DireEffect effect;
/*    */   protected boolean forceFinish = false;
/*    */   private boolean addedDFX;
/*    */   protected float timePassed;
/*    */   protected double duration;
/*    */   
/*    */   public ExecuteDFXCommand(PropNode propNode, DireEffect effect, float serverTime) {
/* 21 */     this.propNode = propNode;
/* 22 */     this.effect = effect;
/* 23 */     this.timePassed = serverTime;
/* 24 */     this.duration = effect.getDescription().getDuration();
/*    */   }
/*    */   
/*    */   public String getName() {
/* 28 */     return "use-item";
/*    */   }
/*    */   
/*    */   public String getDireEffectId() {
/* 32 */     return this.effect.getDescription().getId();
/*    */   }
/*    */   
/*    */   public boolean isFinished() {
/* 36 */     return (this.timePassed >= this.duration || this.forceFinish || this.effect.isFinished());
/*    */   }
/*    */   
/*    */   public void forceFinish() {
/* 40 */     this.effect.forceFinish();
/* 41 */     this.forceFinish = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(float time) {
/* 46 */     if (!this.addedDFX) {
/*    */ 
/*    */       
/* 49 */       this.effect.update(this.timePassed);
/* 50 */       this.propNode.addDfx(this.effect);
/* 51 */       this.addedDFX = true;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 56 */     this.timePassed += time;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\command\ExecuteDFXCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */