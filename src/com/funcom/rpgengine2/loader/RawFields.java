/*    */ package com.funcom.rpgengine2.loader;
/*    */ 
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RawFields
/*    */ {
/*    */   private String[] fields;
/*    */   private int index;
/*    */   
/*    */   public RawFields(String[] fields) {
/* 15 */     this.fields = fields;
/*    */   }
/*    */   
/*    */   public boolean hasMore() {
/* 19 */     return (this.index < this.fields.length);
/*    */   }
/*    */   
/*    */   public String next() {
/* 23 */     if (this.index >= this.fields.length) {
/* 24 */       throw new NoSuchElementException("no more fields");
/*    */     }
/*    */     
/* 27 */     return this.fields[this.index++];
/*    */   }
/*    */   
/*    */   public String current() {
/* 31 */     return this.fields[this.index];
/*    */   }
/*    */   
/*    */   public String toString() {
/* 35 */     String data = "";
/*    */     
/* 37 */     if (this.index > 0) {
/* 38 */       data = "(" + this.fields[this.index - 1] + ")";
/*    */     }
/*    */     
/* 41 */     return "[last read field num=" + this.index + ", DATA: " + (data.isEmpty() ? this.fields[0] : data) + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loader\RawFields.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */