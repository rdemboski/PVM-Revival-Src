/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.Priority;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DfxPlayVisual
/*    */   implements VisualEffect
/*    */ {
/* 13 */   private static final Logger LOGGER = Logger.getLogger(DfxPlayVisual.class);
/*    */   
/*    */   private PropNode propNode;
/*    */   
/*    */   DfxPlayVisual(PropNode propNode) {
/* 18 */     this.propNode = propNode;
/*    */   }
/*    */ 
/*    */   
/*    */   public void apply(Object oldValue, Object newValue) {
/* 23 */     if (!(newValue instanceof String)) {
/* 24 */       if (LOGGER.isEnabledFor((Priority)Level.DEBUG)) {
/* 25 */         LOGGER.debug("Nonvisual property fired");
/*    */       }
/*    */       return;
/*    */     } 
/* 29 */     String dfxName = (String)newValue;
/* 30 */     dfxName = this.propNode.getProp().getMappedDfx(dfxName);
/* 31 */     if ((dfxName.equals("idle") || dfxName.equals("move")) && this.propNode.hasNonStandardAnimationRunning())
/*    */       return; 
/* 33 */     this.propNode.playDfx(dfxName);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\DfxPlayVisual.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */