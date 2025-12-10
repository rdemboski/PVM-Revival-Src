/*    */ package com.funcom.tcg.client.controllers;
/*    */ 
/*    */ import com.funcom.commons.MathUtils;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.command.Command;
/*    */ import com.funcom.gameengine.model.command.IdleCommand;
/*    */ import com.funcom.gameengine.model.command.InterpolateMoveCommand;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.jme.scene.Controller;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InterpolationController
/*    */   extends Controller
/*    */ {
/*    */   private Creature prop;
/*    */   private boolean moving = false;
/*    */   private InterpolateMoveCommand moveCommand;
/*    */   
/*    */   public InterpolationController(PropNode propNode) {
/* 22 */     this.prop = (Creature)propNode.getProp();
/* 23 */     this.moving = false;
/* 24 */     this.moveCommand = new InterpolateMoveCommand(this.prop);
/*    */   }
/*    */   
/*    */   public void setPosition(WorldCoordinate coordinate) {
/* 28 */     this.moveCommand.setPosition(coordinate);
/*    */   }
/*    */   
/*    */   public void setAngle(float angle) {
/* 32 */     this.moveCommand.setAngle(angle);
/*    */   }
/*    */   
/*    */   public void moveToPosition(WorldCoordinate coordinate, float angle) {
/* 36 */     this.moveCommand.moveToPosition(coordinate, angle);
/* 37 */     double distance = this.moveCommand.getDistance();
/* 38 */     double angleDiff = Math.abs(MathUtils.getAngleDiff(this.prop.getRotation(), angle));
/* 39 */     if ((angleDiff >= 0.001D || distance >= 0.001D) && !this.moving) {
/* 40 */       this.prop.immediateCommand((Command)this.moveCommand);
/* 41 */       this.moving = true;
/* 42 */     } else if (distance < 0.001D && this.moving) {
/* 43 */       stop();
/*    */     }
/* 45 */     else if (this.prop.getCurrentCommand() == this.moveCommand.getIdleCommand()) {
/* 46 */       this.prop.immediateCommand((Command)this.moveCommand);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void stop() {
/* 51 */     this.moveCommand.moveToEnd();
/* 52 */     if (this.prop.getCurrentCommand().equals(this.moveCommand))
/* 53 */       this.prop.immediateCommand((Command)new IdleCommand()); 
/* 54 */     this.moving = false;
/*    */   }
/*    */   
/*    */   public void update(float time) {}
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\controllers\InterpolationController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */