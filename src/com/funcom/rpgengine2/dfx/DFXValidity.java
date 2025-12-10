/*    */ package com.funcom.rpgengine2.dfx;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DFXValidity
/*    */ {
/* 11 */   private static final List<String> EMPTY = Collections.unmodifiableList(new ArrayList<String>(0));
/*    */   
/*    */   private final String itemId;
/*    */   
/*    */   private List<String> missingReferences;
/*    */   private List<String> unusedReferences;
/*    */   
/*    */   public DFXValidity(String itemId) {
/* 19 */     this.itemId = itemId;
/*    */   }
/*    */   
/*    */   public void addMissingItemReference(String missingReference) {
/* 23 */     if (this.missingReferences == null) {
/* 24 */       this.missingReferences = new ArrayList<String>();
/*    */     }
/* 26 */     this.missingReferences.add(missingReference);
/*    */   }
/*    */   
/*    */   public void addUnusedItemReference(String unusedReference) {
/* 30 */     if (this.unusedReferences == null) {
/* 31 */       this.unusedReferences = new ArrayList<String>();
/*    */     }
/* 33 */     this.unusedReferences.add(unusedReference);
/*    */   }
/*    */   
/*    */   public boolean isValid() {
/* 37 */     return (this.unusedReferences == null && this.missingReferences == null);
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 41 */     String ret = "itemId=" + this.itemId;
/*    */     
/* 43 */     if (this.missingReferences != null) {
/* 44 */       for (String missingReference : this.missingReferences) {
/* 45 */         ret = ret + "\n    Missing Reference in Item: " + missingReference;
/*    */       }
/*    */     }
/* 48 */     if (this.unusedReferences != null) {
/* 49 */       for (String unusedReference : this.unusedReferences) {
/* 50 */         ret = ret + "\n    Unused Reference in Item: " + unusedReference;
/*    */       }
/*    */     }
/*    */     
/* 54 */     return ret;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 58 */     return "DFXValidity{message=" + getMessage() + '}';
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getMissingReferences() {
/* 64 */     return (this.missingReferences == null) ? EMPTY : this.missingReferences;
/*    */   }
/*    */   
/*    */   public List<String> getUnusedReferences() {
/* 68 */     return (this.unusedReferences == null) ? EMPTY : this.unusedReferences;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\dfx\DFXValidity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */