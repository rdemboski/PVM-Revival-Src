/*    */ package com.funcom.gameengine.model;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Checkpoint
/*    */   extends InteractibleProp
/*    */ {
/* 13 */   private double width = 0.0D;
/* 14 */   private double height = 0.0D;
/* 15 */   private String dfxText = "";
/* 16 */   private String dfxScript = "";
/*    */   
/*    */   private boolean savable;
/*    */   
/*    */   public Checkpoint(String name, WorldCoordinate worldCoordinate) {
/* 21 */     super(-1, name, worldCoordinate, 0.0D);
/*    */   }
/*    */   
/*    */   public Checkpoint(int id, String name, WorldCoordinate worldCoordinate, String dfxText, String dfxScript, boolean savable) {
/* 25 */     super(id, name, worldCoordinate, 0.0D);
/* 26 */     this.dfxText = dfxText;
/* 27 */     this.dfxScript = dfxScript;
/* 28 */     this.savable = savable;
/*    */   }
/*    */   
/*    */   public double getWidth() {
/* 32 */     return this.width;
/*    */   }
/*    */   
/*    */   public void setWidth(double width) {
/* 36 */     this.width = width;
/*    */   }
/*    */   
/*    */   public double getHeight() {
/* 40 */     return this.height;
/*    */   }
/*    */   
/*    */   public void setHeight(double height) {
/* 44 */     this.height = height;
/*    */   }
/*    */   
/*    */   public String getDfxText() {
/* 48 */     return this.dfxText;
/*    */   }
/*    */   
/*    */   public String getDfxScript() {
/* 52 */     return this.dfxScript;
/*    */   }
/*    */   
/*    */   public boolean isSavable() {
/* 56 */     return this.savable;
/*    */   }
/*    */   
/*    */   public void setSavable(boolean savable) {
/* 60 */     this.savable = savable;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 66 */     return "Checkpoint{width=" + this.width + ", height=" + this.height + ", dfxText='" + this.dfxText + '\'' + ", dfxScript='" + this.dfxScript + '\'' + ", savable=" + this.savable + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\Checkpoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */