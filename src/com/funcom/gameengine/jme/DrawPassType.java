/*    */ package com.funcom.gameengine.jme;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum DrawPassType
/*    */ {
/* 13 */   MAP_CONTENT_BELOW_GROUND(true),
/* 14 */   TILES(true),
/* 15 */   DECALS(true)
/*    */   {
/*    */     public void init(Renderer r) {
/* 18 */       r.setPolygonOffset(-5.0F, -5.0F);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public void cleanup(Renderer r) {
/* 24 */       r.clearPolygonOffset();
/*    */     } },
/* 26 */   RUNTIME_CONTENT(true),
/* 27 */   MAP_CONTENT_ABOVE_GROUND(true),
/* 28 */   TRANSPARENT_CONTENT(true),
/* 29 */   DEVELOPMENT_CONTENT(true),
/* 30 */   UNKNOWN_PASS_TYPE(true);
/*    */   static {
/* 32 */     DEFAULT_STATES = new DrawPassType[] { MAP_CONTENT_BELOW_GROUND, TILES, DECALS, RUNTIME_CONTENT, MAP_CONTENT_ABOVE_GROUND, TRANSPARENT_CONTENT, DEVELOPMENT_CONTENT, UNKNOWN_PASS_TYPE };
/*    */   }
/*    */ 
/*    */   
/*    */   public static final DrawPassType[] DEFAULT_STATES;
/*    */   
/*    */   private final boolean renderQueueFlushed;
/*    */ 
/*    */   
/*    */   DrawPassType(boolean renderQueueFlushed) {
/* 42 */     this.renderQueueFlushed = renderQueueFlushed;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(Renderer r) {}
/*    */ 
/*    */   
/*    */   public void cleanup(Renderer r) {}
/*    */ 
/*    */   
/*    */   public boolean isRenderQueueFlushed() {
/* 54 */     return this.renderQueueFlushed;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\DrawPassType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */