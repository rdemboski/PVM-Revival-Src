/*    */ package com.funcom.gameengine.pathfinding2;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoPathException
/*    */   extends Exception
/*    */ {
/*    */   public NoPathException() {}
/*    */   
/*    */   public NoPathException(String message) {
/* 32 */     super(message);
/*    */   }
/*    */   
/*    */   public NoPathException(AStarNode startNode, AStarNode endNode) {
/* 36 */     super(String.format("Couldn't find path! startNode:'%s' | endNode:'%s'", new Object[] { startNode, endNode }));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\NoPathException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */