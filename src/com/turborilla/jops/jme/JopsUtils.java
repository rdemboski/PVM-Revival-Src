/*    */ package com.turborilla.jops.jme;
/*    */ 
/*    */ import com.jme.renderer.Camera;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import org.openmali.vecmath2.Matrix4f;
/*    */ import org.openmali.vecmath2.Point3f;
/*    */ import org.softmed.jops.Generator;
/*    */ import org.softmed.jops.ParticleSystem;
/*    */ 
/*    */ public final class JopsUtils {
/*    */   public static Set<ParticleGeneratorMesh> createParticleGeneratorMeshes(ParticleSystem particleSystem, Camera camera) {
/* 13 */     fixRotation(particleSystem);
/* 14 */     fixPosition(particleSystem);
/*    */     
/* 16 */     Set<ParticleGeneratorMesh> results = new HashSet<ParticleGeneratorMesh>();
/* 17 */     for (Generator generator : particleSystem.getGenerators())
/* 18 */       results.add(createGenerator(generator, camera)); 
/* 19 */     return results;
/*    */   }
/*    */   
/*    */   private static void fixPosition(ParticleSystem particleSystem) {
/* 23 */     if (particleSystem.getPosition() == null)
/* 24 */       particleSystem.setPosition(new Point3f()); 
/*    */   }
/*    */   
/*    */   private static void fixRotation(ParticleSystem particleSystem) {
/* 28 */     if (particleSystem.getRotation() == null) {
/* 29 */       particleSystem.setRotation(new Matrix4f(new float[] { 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F }));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static ParticleGeneratorMesh createGenerator(Generator generator, Camera camera) {
/* 41 */     ParticleGeneratorMesh particleGenerator = new ParticleGeneratorMesh(generator.getName(), generator);
/* 42 */     particleGenerator.setCamera(camera);
/* 43 */     return particleGenerator;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\turborilla\jops\jme\JopsUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */