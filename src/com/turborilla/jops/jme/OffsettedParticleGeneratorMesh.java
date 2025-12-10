/*    */ package com.turborilla.jops.jme;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import org.softmed.jops.Generator;
/*    */ 
/*    */ 
/*    */ public class OffsettedParticleGeneratorMesh
/*    */   extends ParticleGeneratorMesh
/*    */ {
/*    */   protected float factor;
/*    */   protected float offset;
/*    */   
/*    */   public OffsettedParticleGeneratorMesh(Generator generator, float factor, float offset) {
/* 14 */     super(generator.getName(), generator);
/* 15 */     this.factor = factor;
/* 16 */     this.offset = offset;
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(Renderer r) {
/* 21 */     if (this.triangleQuantity > 0) {
/*    */       
/* 23 */       r.setPolygonOffset(this.factor, this.offset);
/* 24 */       super.draw(r);
/* 25 */       r.clearPolygonOffset();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\turborilla\jops\jme\OffsettedParticleGeneratorMesh.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */