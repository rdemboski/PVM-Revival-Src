/*    */ package com.funcom.commons.jme.md5importer.importer.resource;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Block
/*    */ {
/*    */   private ArrayList<TokenizedLine> tokenizedLines;
/*    */   
/*    */   public Block() {
/* 17 */     this.tokenizedLines = new ArrayList<TokenizedLine>();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Block(Block block, int copyStart, int copyEnd) {
/* 27 */     for (int i = copyStart; i < copyEnd && i < block.getLineCount(); i++) {
/* 28 */       this.tokenizedLines.add(block.tokenizedLines.get(i));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ArrayList<TokenizedLine> getTokenizedLines() {
/* 36 */     return this.tokenizedLines;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLineCount() {
/* 44 */     return this.tokenizedLines.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\importer\resource\Block.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */