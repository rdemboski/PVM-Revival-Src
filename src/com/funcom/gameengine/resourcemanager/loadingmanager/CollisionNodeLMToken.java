/*    */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import java.awt.Point;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CollisionNodeLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/* 17 */   WorldCoordinate coord = null;
/* 18 */   Point tileCoord = null;
/* 19 */   String ident = null;
/* 20 */   HashMap<String, WorldCoordinate> collisionNodes = null;
/*    */   
/*    */   public CollisionNodeLMToken(WorldCoordinate coord, Point tileCoord, String ident, HashMap<String, WorldCoordinate> collisionNodes) {
/* 23 */     this.coord = coord;
/* 24 */     this.tileCoord = tileCoord;
/* 25 */     this.ident = ident;
/* 26 */     this.collisionNodes = collisionNodes;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int update() {
/* 33 */     this.coord.addTiles(this.tileCoord);
/* 34 */     this.collisionNodes.put(this.ident, this.coord);
/*    */     
/* 36 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\CollisionNodeLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */