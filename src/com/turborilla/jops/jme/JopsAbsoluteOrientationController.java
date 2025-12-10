/*    */ package com.turborilla.jops.jme;
/*    */ 
/*    */ import com.jme.math.Matrix4f;
/*    */ import com.jme.scene.Controller;
/*    */ import com.jme.scene.Spatial;
/*    */ import org.softmed.jops.ParticleSystem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JopsAbsoluteOrientationController
/*    */   extends Controller
/*    */ {
/*    */   private ParticleSystem particleSystem;
/*    */   private Spatial referenceSpatial;
/*    */   private Matrix4f tempRotation;
/*    */   private float[] position;
/*    */   private float[] rotation;
/*    */   
/*    */   public JopsAbsoluteOrientationController(ParticleSystem particleSystem, Spatial referenceSpatial) {
/* 22 */     this.particleSystem = particleSystem;
/* 23 */     this.referenceSpatial = referenceSpatial;
/* 24 */     this.tempRotation = new Matrix4f();
/* 25 */     this.position = new float[3];
/* 26 */     this.rotation = new float[16];
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(float v) {
/* 31 */     this.particleSystem.getPosition().set(this.referenceSpatial.getWorldTranslation().toArray(this.position));
/*    */     
/* 33 */     this.referenceSpatial.getWorldRotation().toRotationMatrix(this.tempRotation).get(this.rotation);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 41 */     this.particleSystem.getRotation().set(this.rotation[0], this.rotation[1], this.rotation[2], this.rotation[3], this.rotation[4], this.rotation[5], this.rotation[6], this.rotation[7], this.rotation[8], this.rotation[9], this.rotation[10], this.rotation[11], this.rotation[12], this.rotation[13], this.rotation[14], this.rotation[15]);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\turborilla\jops\jme\JopsAbsoluteOrientationController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */