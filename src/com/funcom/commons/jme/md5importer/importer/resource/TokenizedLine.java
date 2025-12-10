/*    */ package com.funcom.commons.jme.md5importer.importer.resource;
/*    */ 
/*    */ import com.funcom.commons.jme.md5importer.importer.resource.token.Md5Token;
/*    */ import java.util.ArrayList;
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
/*    */ public class TokenizedLine
/*    */ {
/* 20 */   private ArrayList<Md5Token> tokens = new ArrayList<Md5Token>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(Md5Token token) {
/* 28 */     this.tokens.add(token);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Md5Token get(int index) {
/* 37 */     if (index >= 0 && index < size()) {
/* 38 */       return this.tokens.get(index);
/*    */     }
/* 40 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean remove(Md5Token token) {
/* 49 */     return this.tokens.remove(token);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ArrayList<Md5Token> getAllTokens() {
/* 57 */     return this.tokens;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean contains(Md5Token token) {
/* 66 */     return this.tokens.contains(token);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int size() {
/* 74 */     return this.tokens.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\importer\resource\TokenizedLine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */