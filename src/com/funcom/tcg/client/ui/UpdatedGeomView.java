/*     */ package com.funcom.tcg.client.ui;
/*     */ 
/*     */ import com.funcom.gameengine.model.input.Cursor;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.state.TCGCursorSetter;
/*     */ import com.jmex.bui.event.MouseAdapter;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.event.MouseWheelListener;
/*     */ 
/*     */ public class UpdatedGeomView
/*     */   extends AbstactAngledGeomView {
/*     */   private static final float DEFAULT_ROTATION_FRICTION = 4.0F;
/*     */   private static final float MAX_SPEED = 10.0F;
/*     */   private float rotationVelocity;
/*     */   private float friction;
/*     */   private float acceleration;
/*     */   private boolean smoothStop;
/*     */   private boolean isPet = false;
/*     */   
/*     */   public UpdatedGeomView() {
/*  22 */     this.friction = 4.0F;
/*     */   }
/*     */   
/*     */   public UpdatedGeomView(boolean isPet) {
/*  26 */     this.friction = 4.0F;
/*  27 */     if (isPet) {
/*  28 */       this.rotationVelocity = 0.0F;
/*     */     }
/*     */   }
/*     */   
/*     */   protected void updateCharacterAngle(float time) {
/*  33 */     PropNode geometry = getGeometry();
/*  34 */     if (geometry == null) {
/*     */       return;
/*     */     }
/*  37 */     float angle = geometry.getAngle();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  47 */     angle += this.rotationVelocity * time;
/*     */     
/*  49 */     if (this.smoothStop) {
/*  50 */       if (this.rotationVelocity < 0.0F) {
/*  51 */         this.rotationVelocity = Math.min(0.0F, this.rotationVelocity - this.rotationVelocity * this.friction * time);
/*     */       } else {
/*  53 */         this.rotationVelocity = Math.max(0.0F, this.rotationVelocity - this.rotationVelocity * this.friction * time);
/*     */       } 
/*  55 */     } else if (this.acceleration != 0.0F && Math.abs(this.rotationVelocity + this.acceleration) < 10.0F) {
/*  56 */       this.rotationVelocity += this.acceleration;
/*     */     } 
/*     */     
/*  59 */     angle %= 6.2831855F;
/*  60 */     geometry.setAngle(angle);
/*     */   }
/*     */   
/*     */   public void resetCharacterAngle() {
/*  64 */     PropNode geometry = getGeometry();
/*  65 */     if (geometry == null)
/*     */       return; 
/*  67 */     geometry.setAngle(-0.7853982F);
/*     */   }
/*     */   
/*     */   public float getFriction() {
/*  71 */     return this.friction;
/*     */   }
/*     */   
/*     */   public void setFriction(float friction) {
/*  75 */     this.friction = friction;
/*     */   }
/*     */   
/*     */   public float getRotationVelocity() {
/*  79 */     return this.rotationVelocity;
/*     */   }
/*     */   
/*     */   public void smoothIncrease(float rotationAcceleration) {
/*  83 */     this.acceleration = rotationAcceleration;
/*  84 */     this.smoothStop = false;
/*     */   }
/*     */   
/*     */   public void setRotationVelocity(float rotationVelocity) {
/*  88 */     this.rotationVelocity = rotationVelocity;
/*  89 */     this.acceleration = 0.0F;
/*  90 */     this.smoothStop = false;
/*     */   }
/*     */   
/*     */   public void smoothStop() {
/*  94 */     this.smoothStop = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class CharacterRotationListener
/*     */     extends MouseAdapter
/*     */   {
/*     */     private static final float MOUSE_ROTATION_TRANSLATION = 0.5F;
/*     */     
/*     */     private int prevX;
/*     */     private UpdatedGeomView that;
/*     */     private boolean mouseIn = false;
/*     */     
/*     */     public CharacterRotationListener(UpdatedGeomView that) {
/* 108 */       this.that = that;
/*     */     }
/*     */ 
/*     */     
/*     */     public void mousePressed(MouseEvent event) {
/* 113 */       this.prevX = event.getX();
/* 114 */       this.that.rotationVelocity = 0.0F;
/* 115 */       this.that.smoothStop();
/* 116 */       MainGameState.getMouseCursorSetter().setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_DRAG_CLOSE);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseReleased(MouseEvent event) {
/* 122 */       this.that.setRotationVelocity(0.0F);
/* 123 */       MainGameState.getMouseCursorSetter().setCursor(this.mouseIn ? (Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_DRAG : (Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_WALK);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseDragged(MouseEvent event) {
/* 130 */       int newX = event.getX();
/* 131 */       this.that.rotationVelocity = (this.prevX - newX) * 0.5F;
/* 132 */       if (this.that.rotationVelocity < 0.0F) {
/* 133 */         this.that.rotationVelocity = Math.max(this.that.rotationVelocity, -360.0F);
/* 134 */       } else if (this.that.rotationVelocity > 0.0F) {
/* 135 */         this.that.rotationVelocity = Math.min(this.that.rotationVelocity, 360.0F);
/*     */       } 
/* 137 */       this.prevX = newX;
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseEntered(MouseEvent event) {
/* 142 */       super.mouseEntered(event);
/* 143 */       this.mouseIn = true;
/* 144 */       MainGameState.getMouseCursorSetter().setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_DRAG);
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseExited(MouseEvent event) {
/* 149 */       super.mouseExited(event);
/* 150 */       this.mouseIn = false;
/* 151 */       MainGameState.getMouseCursorSetter().setCursor((Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_WALK);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class CharacterZoomListener
/*     */     implements MouseWheelListener
/*     */   {
/* 160 */     private float zoom = 1.0F;
/* 161 */     private float MAX_ZOOM = 2.0F;
/* 162 */     private float MIN_ZOOM = 0.5F;
/*     */     private UpdatedGeomView that;
/*     */     
/*     */     public CharacterZoomListener(UpdatedGeomView that) {
/* 166 */       this.that = that;
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseWheeled(MouseEvent event) {
/* 171 */       if (event.getDelta() > 0) {
/* 172 */         this.zoom += 0.1F;
/* 173 */         this.zoom = (this.zoom > this.MAX_ZOOM) ? this.MAX_ZOOM : this.zoom;
/*     */       } else {
/* 175 */         this.zoom -= 0.1F;
/* 176 */         this.zoom = (this.zoom < this.MIN_ZOOM) ? this.MIN_ZOOM : this.zoom;
/*     */       } 
/* 178 */       this.that.setZoom(this.zoom);
/* 179 */       System.out.println("zoom" + this.zoom);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\UpdatedGeomView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */