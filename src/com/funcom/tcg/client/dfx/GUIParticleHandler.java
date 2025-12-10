/*    */ package com.funcom.tcg.client.dfx;
/*    */ 
/*    */ import com.funcom.commons.dfx.Effect;
/*    */ import com.funcom.commons.dfx.EffectHandler;
/*    */ import com.funcom.commons.dfx.GUIParticleDescription;
/*    */ import com.funcom.gameengine.view.RepresentationalNode;
/*    */ import com.funcom.gameengine.view.particles.GuiParticleJoint;
/*    */ import com.funcom.gameengine.view.particles.GuiParticlesRenderPass;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.jme.math.Vector2f;
/*    */ import com.jme.system.DisplaySystem;
/*    */ import org.softmed.jops.ParticleSystem;
/*    */ 
/*    */ 
/*    */ public class GUIParticleHandler
/*    */   implements EffectHandler
/*    */ {
/*    */   private boolean done = false;
/*    */   
/*    */   public GUIParticleHandler(RepresentationalNode representationalNode) {}
/*    */   
/*    */   public void startEffect(Effect sourceEffect) {
/* 24 */     GUIParticleDescription desc = (GUIParticleDescription)sourceEffect.getDescription();
/* 25 */     GuiParticlesRenderPass renderPass = MainGameState.getGuiParticlesRenderPass();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 33 */     if (renderPass.hasJoint(desc.getName())) {
/* 34 */       renderPass.triggerParticleEffect(desc.getName());
/*    */     } else {
/* 36 */       ParticleSystem particles = (ParticleSystem)TcgGame.getResourceManager().getResource(ParticleSystem.class, desc.getResource());
/* 37 */       GuiParticleJoint guiParticle = new GuiParticleJoint(desc.getName(), new Vector2f(calcX(desc), calcY(desc)), particles, DisplaySystem.getDisplaySystem().getRenderer().getCamera());
/* 38 */       renderPass.addGuiParticleJoint(guiParticle);
/* 39 */       renderPass.triggerParticleEffect(desc.getName());
/*    */     } 
/*    */     
/* 42 */     this.done = true;
/*    */   }
/*    */   
/*    */   private float calcY(GUIParticleDescription desc) {
/* 46 */     if (desc.getReferencePoint().equals("BottomLeft") || desc.getReferencePoint().equals("BottomRight")) {
/* 47 */       return desc.getY();
/*    */     }
/* 49 */     return (DisplaySystem.getDisplaySystem().getHeight() - desc.getY());
/*    */   }
/*    */ 
/*    */   
/*    */   private float calcX(GUIParticleDescription desc) {
/* 54 */     if (desc.getReferencePoint().equals("TopLeft") || desc.getReferencePoint().equals("BottomLeft")) {
/* 55 */       return desc.getX();
/*    */     }
/* 57 */     return (DisplaySystem.getDisplaySystem().getWidth() - desc.getX());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void endEffect(Effect sourceEffect) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFinished() {
/* 67 */     return this.done;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\dfx\GUIParticleHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */