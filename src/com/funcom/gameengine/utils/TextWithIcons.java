/*    */ package com.funcom.gameengine.utils;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextWithIcons
/*    */ {
/*    */   private Matcher matcher;
/*    */   private String textWithIcons;
/* 12 */   static Pattern pat = Pattern.compile("\\[(.*?\\.png)\\]");
/*    */   
/*    */   public enum TokenType {
/* 15 */     IMAGE, TEXT, NULL;
/*    */   }
/*    */   
/* 18 */   private int currentPos = 0;
/*    */   private TokenType nextTokenType;
/*    */   private String nextToken;
/*    */   
/*    */   public TextWithIcons(String text) {
/* 23 */     this.textWithIcons = text;
/* 24 */     this.matcher = pat.matcher(text);
/* 25 */     findNext();
/*    */   }
/*    */   
/*    */   public TokenType getNextType() {
/* 29 */     return this.nextTokenType;
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 33 */     return (this.nextTokenType != TokenType.NULL);
/*    */   }
/*    */   
/*    */   public String getNext() {
/* 37 */     String current = this.nextToken;
/* 38 */     findNext();
/* 39 */     return current;
/*    */   }
/*    */   
/*    */   private void findNext() {
/*    */     int end;
/* 44 */     if (this.currentPos >= this.textWithIcons.length()) {
/* 45 */       this.nextToken = null;
/* 46 */       this.nextTokenType = TokenType.NULL;
/*    */       
/*    */       return;
/*    */     } 
/* 50 */     boolean foundMatch = this.matcher.find(this.currentPos);
/*    */     
/* 52 */     if (!foundMatch) { end = this.textWithIcons.length(); }
/* 53 */     else { end = this.matcher.start(); }
/*    */     
/* 55 */     if (end == this.currentPos) {
/* 56 */       this.nextToken = this.matcher.group(1);
/* 57 */       this.currentPos = this.matcher.end();
/* 58 */       this.nextTokenType = TokenType.IMAGE;
/*    */     } else {
/*    */       
/* 61 */       this.nextToken = this.textWithIcons.substring(this.currentPos, end);
/* 62 */       this.currentPos = end;
/* 63 */       this.nextTokenType = TokenType.TEXT;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getOriginalTextWithIcons() {
/* 68 */     return this.textWithIcons;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengin\\utils\TextWithIcons.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */