/*    */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*    */ 
/*    */ import com.funcom.commons.geom.LineWCHeight;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.spatial.LineNode;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CollisionLineLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/* 18 */   String startIdent = null;
/* 19 */   String endIdent = "";
/* 20 */   double height = 0.0D;
/* 21 */   LineNode lineRoot = null;
/* 22 */   HashMap<String, WorldCoordinate> collisionNodes = null;
/*    */   
/*    */   public CollisionLineLMToken(String startIdent, String endIdent, double height, LineNode lineRoot, HashMap<String, WorldCoordinate> collisionNodes) {
/* 25 */     this.startIdent = startIdent;
/* 26 */     this.endIdent = endIdent;
/* 27 */     this.height = height;
/* 28 */     this.lineRoot = lineRoot;
/* 29 */     this.collisionNodes = collisionNodes;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int update() {
/* 36 */     WorldCoordinate startCoord = this.collisionNodes.get(this.startIdent);
/* 37 */     WorldCoordinate endCoord = this.collisionNodes.get(this.endIdent);
/* 38 */     LineWCHeight line = new LineWCHeight(startCoord, endCoord, this.height);
/* 39 */     this.lineRoot.addLine(line);
/*    */     
/* 41 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\CollisionLineLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */