/*    */ package com.funcom.gameengine.pathfinding2;
/*    */ 
/*    */ import com.funcom.commons.FastMathUtils;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.jme.math.FastMath;
/*    */ import com.jme.math.Vector2f;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BezierInterpolator
/*    */   implements WaypointBuilder
/*    */ {
/*    */   public static final String TYPE = "bezier";
/*    */   private WaypointBuilder waypointBuilder;
/*    */   private float spacing;
/*    */   
/*    */   public BezierInterpolator(WaypointBuilder waypointBuilder, float spacing) {
/* 22 */     if (waypointBuilder == null)
/* 23 */       throw new IllegalArgumentException("waypointBuilder = null"); 
/* 24 */     this.waypointBuilder = waypointBuilder;
/* 25 */     this.spacing = spacing;
/*    */   }
/*    */   
/*    */   public List<WorldCoordinate> makeWaypoints(AStarNode path) {
/* 29 */     List<WorldCoordinate> decoratorWaypoints = this.waypointBuilder.makeWaypoints(path);
/* 30 */     List<WorldCoordinate> returnableWaypoints = new LinkedList<WorldCoordinate>();
/* 31 */     returnableWaypoints.add(decoratorWaypoints.get(0));
/*    */ 
/*    */ 
/*    */     
/* 35 */     for (int i = 0; i < decoratorWaypoints.size() - 2; i += 2) {
/*    */       
/* 37 */       WorldCoordinate firstWaypoint = decoratorWaypoints.get(i);
/* 38 */       WorldCoordinate secondWaypoint = decoratorWaypoints.get(i + 1);
/* 39 */       WorldCoordinate thirdWaypoint = decoratorWaypoints.get(i + 2);
/*    */ 
/*    */ 
/*    */       
/* 43 */       float cumulativeDistance = (float)firstWaypoint.distanceTo(secondWaypoint) + (float)secondWaypoint.distanceTo(thirdWaypoint);
/* 44 */       int numberOfSteps = (int)FastMath.floor(cumulativeDistance / this.spacing);
/*    */ 
/*    */       
/* 47 */       float step = 1.0F / numberOfSteps;
/* 48 */       Vector2f p0 = FastMathUtils.toVector2f(firstWaypoint);
/* 49 */       Vector2f p1 = FastMathUtils.toVector2f(secondWaypoint);
/* 50 */       Vector2f p2 = FastMathUtils.toVector2f(thirdWaypoint);
/* 51 */       Vector2f retVec = new Vector2f();
/*    */       
/*    */       float f;
/* 54 */       for (f = 0.0F; f < 1.0F; f += step) {
/* 55 */         FastMathUtils.bezierQuadraticInterpolation(f, retVec, p0, p1, p2);
/* 56 */         returnableWaypoints.add(FastMathUtils.toWorldCoordinate(retVec));
/*    */       } 
/*    */     } 
/*    */     
/* 60 */     return returnableWaypoints;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\BezierInterpolator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */