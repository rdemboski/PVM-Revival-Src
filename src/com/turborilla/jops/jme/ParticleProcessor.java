/*    */ package com.turborilla.jops.jme;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.softmed.jops.ParticleSystem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParticleProcessor
/*    */ {
/*    */   private boolean alive = true;
/* 13 */   private float calculationTime = 0.02F;
/* 14 */   private float ddt = 0.0F;
/* 15 */   private List<ParticleSystem> systems = new ArrayList<ParticleSystem>();
/* 16 */   private List<ParticleSystem> toRemove = new ArrayList<ParticleSystem>();
/*    */   
/*    */   public void purge() {
/* 19 */     for (ParticleSystem system : this.systems) {
/* 20 */       system.setRenderable(false);
/* 21 */       system.setAlive(false);
/* 22 */       system.setRemove(true);
/*    */     } 
/*    */     
/* 25 */     this.systems.clear();
/*    */   }
/*    */   
/*    */   public void addSystem(ParticleSystem system) {
/* 29 */     this.systems.add(system);
/*    */   }
/*    */   
/*    */   public void removeSystem(ParticleSystem particleSystem) {
/* 33 */     this.systems.remove(particleSystem);
/*    */   }
/*    */   
/*    */   public void setCalculationsPerSecond(int calculationsPerSecond) {
/* 37 */     if (calculationsPerSecond == 0)
/* 38 */       calculationsPerSecond = 45; 
/* 39 */     this.calculationTime = 1.0F / calculationsPerSecond;
/*    */   }
/*    */ 
/*    */   
/* 43 */   private static boolean PARTICLES_ANIMATION_ENABLED = !"false".equalsIgnoreCase(System.getProperty("Debug.PARTICLES_ANIMATION_ENABLED"));
/*    */   public void process(float timelapse) {
/* 45 */     if (!this.alive || !PARTICLES_ANIMATION_ENABLED) {
/*    */       return;
/*    */     }
/* 48 */     this.toRemove.clear();
/*    */     
/* 50 */     float dt = timelapse;
/* 51 */     this.ddt += dt;
/*    */     
/* 53 */     if (this.ddt <= this.calculationTime) {
/*    */       return;
/*    */     }
/*    */     do {
/* 57 */       this.ddt -= this.calculationTime;
/*    */ 
/*    */       
/* 60 */       dt = this.calculationTime;
/*    */       
/* 62 */       for (int j = 0; j < this.systems.size(); j++) {
/* 63 */         ParticleSystem ps = this.systems.get(j);
/* 64 */         ps.processFrame(dt);
/*    */         
/* 66 */         if (ps.isRemove()) {
/* 67 */           this.toRemove.add(ps);
/*    */         }
/*    */       } 
/*    */       
/* 71 */       this.systems.removeAll(this.toRemove);
/* 72 */       for (ParticleSystem ps : this.toRemove) {
/* 73 */         ps.signalRemoved();
/*    */       }
/*    */     }
/* 76 */     while (this.ddt >= this.calculationTime);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\turborilla\jops\jme\ParticleProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */