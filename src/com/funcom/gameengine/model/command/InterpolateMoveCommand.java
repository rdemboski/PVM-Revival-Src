/*     */ package com.funcom.gameengine.model.command;
/*     */ 
/*     */ import com.funcom.commons.MathUtils;
/*     */ import com.funcom.commons.Vector2d;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InterpolateMoveCommand
/*     */   extends Command
/*     */ {
/*     */   public static final double NETWORK_TIMEFRAME = 5.0D;
/*     */   public static final double SPEED_TOLERANCE = 0.001D;
/*     */   private Creature prop;
/*     */   private double distance;
/*     */   private double position;
/*     */   private WorldCoordinate prevCoord;
/*     */   private WorldCoordinate currCoord;
/*     */   private float prevAngle;
/*     */   private float currAngle;
/*     */   private Vector2d moveVec;
/*     */   private Vector2d tickVec;
/*  24 */   private IdleCommand idleCommand = new IdleCommand();
/*     */   
/*     */   public InterpolateMoveCommand(Creature prop) {
/*  27 */     this.prop = prop;
/*     */     
/*  29 */     this.prevCoord = new WorldCoordinate();
/*  30 */     this.currCoord = new WorldCoordinate();
/*  31 */     this.moveVec = new Vector2d();
/*  32 */     this.tickVec = new Vector2d();
/*     */     
/*  34 */     prop.getPosition().copy(this.currCoord);
/*  35 */     this.prevAngle = 0.0F;
/*  36 */     this.currAngle = 0.0F;
/*     */   }
/*     */   
/*     */   public void setPosition(WorldCoordinate coordinate) {
/*  40 */     this.prevCoord.set(coordinate);
/*  41 */     this.currCoord.set(coordinate);
/*     */   }
/*     */   
/*     */   public void setAngle(float angle) {
/*  45 */     this.prevAngle = angle;
/*  46 */     this.currAngle = angle;
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveToPosition(WorldCoordinate coordinate, float angle) {
/*  51 */     this.position = 0.0D;
/*  52 */     this.prevCoord.set(this.currCoord);
/*  53 */     this.currCoord.set(coordinate);
/*  54 */     this.prevAngle = this.prop.getRotation();
/*  55 */     this.currAngle = angle;
/*     */     
/*  57 */     this.prop.setPosition(this.prevCoord);
/*  58 */     this.distance = this.prevCoord.distanceTo(this.currCoord);
/*  59 */     WorldCoordinate moveDiff = this.currCoord.clone().subtract(this.prevCoord);
/*  60 */     this.moveVec.set(moveDiff.getTileCoord().getX() + moveDiff.getTileOffset().getX(), moveDiff.getTileCoord().getY() + moveDiff.getTileOffset().getY());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDistance() {
/*  67 */     return this.distance;
/*     */   }
/*     */   
/*     */   public IdleCommand getIdleCommand() {
/*  71 */     return this.idleCommand;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  75 */     return "move";
/*     */   }
/*     */   
/*     */   public boolean isFinished() {
/*  79 */     return false;
/*     */   }
/*     */   
/*     */   public void update(float time) {
/*  83 */     if (this.distance < 0.001D && Math.abs(this.prevAngle - this.currAngle) < 0.001D) {
/*  84 */       moveToEnd();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  89 */     if (this.position < 1.0D) {
/*  90 */       double mult = time * 5.0D;
/*  91 */       this.position += mult;
/*     */ 
/*     */       
/*  94 */       if (this.position > 1.0D) {
/*  95 */         mult -= this.position - 1.0D;
/*     */       }
/*     */       
/*  98 */       double angleDiff = MathUtils.getAngleDiff(this.prevAngle, this.currAngle);
/*  99 */       float angle = (float)(this.prevAngle + angleDiff * Math.min(1.0D, this.position));
/*     */       
/* 101 */       this.moveVec.mult(this.tickVec, mult);
/* 102 */       this.prop.getPosition().addOffset(this.tickVec);
/* 103 */       this.prop.setRotation(angle);
/*     */     } else {
/* 105 */       moveToEnd();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void moveToEnd() {
/* 110 */     this.prop.setPosition(this.currCoord);
/* 111 */     this.prop.setRotation(this.currAngle);
/* 112 */     this.position = 1.0D;
/* 113 */     if (this.prop.getCurrentCommand().equals(this))
/* 114 */       this.prop.immediateCommand(this.idleCommand); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\command\InterpolateMoveCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */