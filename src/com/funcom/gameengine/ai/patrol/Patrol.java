/*    */ package com.funcom.gameengine.ai.patrol;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class Patrol
/*    */ {
/*    */   private PatrolNode startNode;
/*    */   private String name;
/* 10 */   private Map<Integer, PatrolNode> patrolNodes = new HashMap<Integer, PatrolNode>();
/*    */   
/*    */   public Patrol(String name) {
/* 13 */     this.name = name;
/*    */   }
/*    */   
/*    */   public PatrolNode getStartNode() {
/* 17 */     return this.startNode;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 21 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clearPatrolNodes() {
/* 26 */     this.patrolNodes.clear();
/*    */   }
/*    */   
/*    */   public void insertNode(PatrolNode node) {
/* 30 */     this.patrolNodes.put(Integer.valueOf(node.getOrderNumber()), node);
/*    */     
/* 32 */     if (node.getOrderNumber() == 0) {
/* 33 */       this.startNode = node;
/*    */     }
/*    */   }
/*    */   
/*    */   public void connectNodes(int first, int second) {
/* 38 */     PatrolNode firstNode = this.patrolNodes.get(Integer.valueOf(first));
/* 39 */     PatrolNode secondNode = this.patrolNodes.get(Integer.valueOf(second));
/*    */     
/* 41 */     if (firstNode != null && secondNode != null) {
/* 42 */       firstNode.setNext(secondNode);
/* 43 */       secondNode.setPrevious(firstNode);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\ai\patrol\Patrol.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */