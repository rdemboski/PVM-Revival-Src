/*    */ package com.funcom.gameengine.pathfinding2.debug;
/*    */ 
/*    */ import com.funcom.gameengine.pathfinding2.WorldBoundsGraphNode;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LoggingDebugListener
/*    */   implements DebugListener
/*    */ {
/* 12 */   private static final Logger LOGGER = Logger.getLogger(LoggingDebugListener.class);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 17 */   private StringBuilder builder = new StringBuilder();
/*    */ 
/*    */   
/*    */   public void quadAdded(WorldBoundsGraphNode node) {
/* 21 */     if (!LOGGER.isDebugEnabled())
/*    */       return; 
/* 23 */     this.builder.delete(0, this.builder.length());
/* 24 */     this.builder.append("Quad added: ");
/* 25 */     this.builder.append(node);
/* 26 */     LOGGER.debug(this.builder.toString());
/*    */   }
/*    */   
/*    */   public void merge(WorldBoundsGraphNode node) {
/* 30 */     if (!LOGGER.isDebugEnabled())
/*    */       return; 
/* 32 */     this.builder.delete(0, this.builder.length());
/* 33 */     this.builder.append("Merging: ");
/* 34 */     this.builder.append(node);
/* 35 */     LOGGER.debug(this.builder.toString());
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\debug\LoggingDebugListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */