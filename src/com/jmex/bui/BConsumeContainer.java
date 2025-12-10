/*    */ package com.jmex.bui;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jme.system.DisplaySystem;
/*    */ import com.jmex.bui.event.BEvent;
/*    */ import com.jmex.bui.layout.BLayoutManager;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class BConsumeContainer
/*    */   extends BContainer
/*    */ {
/*    */   public BConsumeContainer() {}
/*    */   
/*    */   public BConsumeContainer(String name) {
/* 15 */     super(name);
/*    */   }
/*    */   
/*    */   public BConsumeContainer(BLayoutManager layout) {
/* 19 */     super(layout);
/*    */   }
/*    */   
/*    */   public BConsumeContainer(String name, BLayoutManager layout) {
/* 23 */     super(name, layout);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean dispatchEvent(BEvent event) {
/* 28 */     super.dispatchEvent(event);
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Renderer renderer) {
/* 36 */     DisplaySystem displaySystem = DisplaySystem.getDisplaySystem();
/* 37 */     int width = displaySystem.getWidth();
/* 38 */     int height = displaySystem.getHeight();
/* 39 */     GL11.glDisable(3089);
/* 40 */     GL11.glScissor(0, 0, width, height);
/*    */ 
/*    */     
/* 43 */     super.render(renderer);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BConsumeContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */