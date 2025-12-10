/*     */ package com.funcom.gameengine.breadcrumbs;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.jme.math.FastMath;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BreadcrumbModel
/*     */   extends Prop
/*     */ {
/*  13 */   private static final Logger LOGGER = Logger.getLogger(BreadcrumbModel.class);
/*     */   
/*     */   public static final String PROP_IN_THE_WORLD = "deleteme";
/*     */   
/*     */   private BreadcrumbModel next;
/*     */   
/*     */   private BreadcrumbModel previous;
/*     */   private Prop player;
/*     */   private WorldCoordinate originalPosition;
/*     */   
/*     */   public BreadcrumbModel(WorldCoordinate position, Prop player) {
/*  24 */     super("Breadcrumb:" + position, position);
/*  25 */     if (position == null)
/*  26 */       throw new IllegalArgumentException("position = null"); 
/*  27 */     if (player == null) {
/*  28 */       throw new IllegalArgumentException("player = null");
/*     */     }
/*  30 */     this.player = player;
/*  31 */     this.originalPosition = new WorldCoordinate(position);
/*     */   }
/*     */   
/*     */   public BreadcrumbModel getPrevious() {
/*  35 */     return this.previous;
/*     */   }
/*     */   
/*     */   public void setPrevious(BreadcrumbModel previous) {
/*  39 */     this.previous = previous;
/*     */   }
/*     */   
/*     */   public BreadcrumbModel getNext() {
/*  43 */     return this.next;
/*     */   }
/*     */   
/*     */   public void setNext(BreadcrumbModel next) {
/*  47 */     this.next = next;
/*     */   }
/*     */   
/*     */   public void setRotation(float rotation) {
/*  51 */     LOGGER.error("Rotation for BreadcrumbNode(s) is calculated and cannot be set, this call is ignored.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRotation() {
/*  66 */     float angle = 0.0F;
/*     */     
/*  68 */     if (this.next != null) {
/*  69 */       angle = (float)getPosition().angleTo(this.next.getPosition());
/*  70 */     } else if (this.previous != null) {
/*  71 */       angle = this.previous.getRotation();
/*     */     } else {
/*  73 */       angle = this.player.getRotation();
/*     */     } 
/*     */     
/*  76 */     return angle - 1.5707964F;
/*     */   }
/*     */   
/*     */   public void update(float time) {
/*  80 */     super.update(time);
/*     */     
/*  82 */     updateShouldHideIfBehindPlayer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateShouldHideIfBehindPlayer() {
/* 110 */     if (!hasNext()) {
/*     */       return;
/*     */     }
/*     */     
/* 114 */     float angleToNext = transferAngle(angleTo(getNext()));
/* 115 */     float angleToPlayer = transferAngle(angleTo(this.player));
/*     */ 
/*     */ 
/*     */     
/* 119 */     boolean shouldRemove = (FastMath.abs(angleToNext - angleToPlayer) < 1.5707964F);
/* 120 */     setVisible(!shouldRemove);
/*     */   }
/*     */   
/*     */   private boolean hasNext() {
/* 124 */     return (getNext() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   private float transferAngle(float angle) {
/* 129 */     if (angle < 0.0F)
/* 130 */       return 6.2831855F + angle; 
/* 131 */     return angle;
/*     */   }
/*     */   
/*     */   public WorldCoordinate getOriginalPosition() {
/* 135 */     return this.originalPosition;
/*     */   }
/*     */   
/*     */   public void resetOriginalPosition() {
/* 139 */     setPosition(this.originalPosition);
/*     */   }
/*     */   
/*     */   public void removeFromWorld() {
/* 143 */     firePropertyChangeListener("deleteme", Boolean.valueOf(true), Boolean.valueOf(false));
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\breadcrumbs\BreadcrumbModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */