/*    */ package com.funcom.tcg.client.state;
/*    */ 
/*    */ import com.funcom.gameengine.model.WorldRenderPass;
/*    */ import com.funcom.gameengine.utils.PerformanceGraphRenderPass;
/*    */ import com.funcom.gameengine.view.BuiRenderPass;
/*    */ import com.funcom.gameengine.view.particles.GuiParticlesRenderPass;
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jme.renderer.pass.BasicPassManager;
/*    */ import com.jme.renderer.pass.Pass;
/*    */ import com.jme.system.DisplaySystem;
/*    */ import com.jmex.effects.glsl.BloomRenderPass;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class TcgBasicPassManager
/*    */   extends BasicPassManager
/*    */ {
/*    */   private WorldRenderPass worldRenderPass;
/*    */   private BuiRenderPass buiRenderPass;
/*    */   private GuiParticlesRenderPass guiParticlesRenderPass;
/*    */   private BloomRenderPass bloomRenderPass;
/*    */   private PerformanceGraphRenderPass performanceGraphRenderPass;
/*    */   
/*    */   public void renderPasses(Renderer r) {
/* 24 */     DisplaySystem displaySystem = DisplaySystem.getDisplaySystem();
/* 25 */     int width = displaySystem.getWidth();
/* 26 */     int height = displaySystem.getHeight();
/*    */     
/* 28 */     for (int i = 0, sSize = this.passes.size(); i < sSize; i++) {
/*    */ 
/*    */       
/* 31 */       GL11.glDisable(3089);
/* 32 */       GL11.glScissor(0, 0, width, height);
/*    */       
/* 34 */       Pass p = this.passes.get(i);
/* 35 */       p.renderPass(r);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setWorldRenderPass(WorldRenderPass worldRenderPass) {
/* 40 */     if (this.worldRenderPass != null)
/* 41 */       remove((Pass)this.worldRenderPass); 
/* 42 */     this.worldRenderPass = worldRenderPass;
/* 43 */     add((Pass)this.worldRenderPass);
/*    */   }
/*    */   
/*    */   public void setBuiRenderPass(BuiRenderPass buiRenderPass) {
/* 47 */     if (this.buiRenderPass != null)
/* 48 */       remove((Pass)this.buiRenderPass); 
/* 49 */     this.buiRenderPass = buiRenderPass;
/* 50 */     add((Pass)this.buiRenderPass);
/*    */   }
/*    */   
/*    */   public void setGuiParticlesRenderPass(GuiParticlesRenderPass guiParticlesRenderPass) {
/* 54 */     if (this.guiParticlesRenderPass != null)
/* 55 */       remove((Pass)this.guiParticlesRenderPass); 
/* 56 */     this.guiParticlesRenderPass = guiParticlesRenderPass;
/* 57 */     add((Pass)this.guiParticlesRenderPass);
/*    */   }
/*    */   
/*    */   public void setBloomRenderPass(BloomRenderPass bloomRenderPass) {
/* 61 */     if (this.bloomRenderPass != null)
/* 62 */       remove((Pass)this.bloomRenderPass); 
/* 63 */     this.bloomRenderPass = bloomRenderPass;
/* 64 */     add((Pass)this.bloomRenderPass);
/*    */   }
/*    */   
/*    */   public void setPerformanceGraphRenderPass(PerformanceGraphRenderPass performanceGraphRenderPass) {
/* 68 */     if (this.performanceGraphRenderPass != null)
/* 69 */       remove((Pass)this.performanceGraphRenderPass); 
/* 70 */     this.performanceGraphRenderPass = performanceGraphRenderPass;
/* 71 */     if (performanceGraphRenderPass != null)
/* 72 */       performanceGraphRenderPass.setEnabled(false); 
/* 73 */     add((Pass)this.performanceGraphRenderPass);
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldRenderPass getWorldRenderPass() {
/* 78 */     return this.worldRenderPass;
/*    */   }
/*    */   
/*    */   public BuiRenderPass getBuiRenderPass() {
/* 82 */     return this.buiRenderPass;
/*    */   }
/*    */   
/*    */   public GuiParticlesRenderPass getGuiParticlesRenderPass() {
/* 86 */     return this.guiParticlesRenderPass;
/*    */   }
/*    */   
/*    */   public BloomRenderPass getBloomRenderPass() {
/* 90 */     return this.bloomRenderPass;
/*    */   }
/*    */   
/*    */   public PerformanceGraphRenderPass getPerformanceGraphRenderPass() {
/* 94 */     return this.performanceGraphRenderPass;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\state\TcgBasicPassManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */