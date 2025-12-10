/*    */ package com.funcom.gameengine.model.command;
/*    */ 
/*    */ import com.funcom.gameengine.Updated;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Command
/*    */   implements Updated
/*    */ {
/* 15 */   public static final Command NOOP = new Command() {
/*    */       public String getName() {
/* 17 */         return "no operation";
/*    */       }
/*    */       
/*    */       public boolean isFinished() {
/* 21 */         return false;
/*    */       }
/*    */ 
/*    */       
/*    */       public void update(float time) {}
/*    */     };
/*    */   
/*    */   private Creature parentCreature;
/*    */   private boolean failed;
/*    */   
/*    */   protected Command() {
/* 32 */     this.failed = false;
/*    */   }
/*    */   
/*    */   public void setParentCreature(Creature parentCreature) {
/* 36 */     this.parentCreature = parentCreature;
/*    */   }
/*    */   
/*    */   public Creature getParentCreature() {
/* 40 */     return this.parentCreature;
/*    */   }
/*    */   
/*    */   protected void fail() {
/* 44 */     this.failed = true;
/*    */   }
/*    */   
/*    */   public boolean isFailed() {
/* 48 */     return this.failed;
/*    */   }
/*    */   
/*    */   public abstract String getName();
/*    */   
/*    */   public abstract boolean isFinished();
/*    */   
/*    */   public String toString() {
/* 56 */     return getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\command\Command.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */