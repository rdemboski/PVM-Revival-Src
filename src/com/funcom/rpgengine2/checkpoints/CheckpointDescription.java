/*    */ package com.funcom.rpgengine2.checkpoints;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CheckpointDescription
/*    */ {
/*    */   private String id;
/*    */   private String name;
/*    */   private String dfxText;
/*    */   private String dfxScript;
/*    */   private boolean savable;
/*    */   
/*    */   public CheckpointDescription(String id, String name, String dfxText, String dfxScript, boolean savable) {
/* 19 */     this.id = id;
/* 20 */     this.name = name;
/* 21 */     this.dfxText = dfxText;
/* 22 */     this.dfxScript = dfxScript;
/* 23 */     this.savable = savable;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 27 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 31 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getDfxText() {
/* 35 */     return this.dfxText;
/*    */   }
/*    */   
/*    */   public String getDfxScript() {
/* 39 */     return this.dfxScript;
/*    */   }
/*    */   
/*    */   public boolean isSavable() {
/* 43 */     return this.savable;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 49 */     return "CheckpointDescription{id='" + this.id + '\'' + ", name='" + this.name + '\'' + ", dfxText='" + this.dfxText + '\'' + ", dfxScript='" + this.dfxScript + '\'' + ", savable=" + this.savable + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\checkpoints\CheckpointDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */