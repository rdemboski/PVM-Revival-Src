/*    */ package com.funcom.gameengine.spatial;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.props.MultipleTargetsModel;
/*    */ import com.funcom.gameengine.model.props.Prop;
/*    */ 
/*    */ 
/*    */ public class Beacon
/*    */   extends Prop
/*    */ {
/*    */   private MultipleTargetsModel.TargetData currentTarget;
/*    */   
/*    */   public Beacon(MultipleTargetsModel.TargetData currentTarget) {
/* 14 */     super("beacon:" + currentTarget.getPosition().toString());
/* 15 */     this.currentTarget = currentTarget;
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldCoordinate getPosition() {
/* 20 */     return this.currentTarget.getPosition();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPosition(WorldCoordinate position) {
/* 25 */     throw new IllegalStateException("This model cannot change position on itself: underlying TargetData has to.");
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\spatial\Beacon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */