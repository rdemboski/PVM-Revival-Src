/*    */ package com.funcom.commons.jme.md5importer.importer.resource;
/*    */ 
/*    */ import com.funcom.commons.jme.md5importer.importer.resource.token.Md5NumberToken;
/*    */ import com.funcom.commons.jme.md5importer.importer.resource.token.Md5StringToken;
/*    */ import com.funcom.commons.jme.md5importer.importer.resource.token.Md5Token;
/*    */ import java.io.IOException;
/*    */ import java.io.StreamTokenizer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Md5Stream
/*    */ {
/*    */   private StreamTokenizer stream;
/* 15 */   private Md5StringToken endToken = new Md5StringToken("}");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Md5Stream(StreamTokenizer stream) {
/* 22 */     this.stream = stream;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Block getNextBlock() throws IOException {
/* 31 */     Block b = new Block();
/* 32 */     TokenizedLine tokenizedLine = getNextTokenizedLine();
/*    */     
/* 34 */     while (tokenizedLine != null && !tokenizedLine.contains((Md5Token)this.endToken)) {
/* 35 */       b.getTokenizedLines().add(tokenizedLine);
/* 36 */       tokenizedLine = getNextTokenizedLine();
/*    */     } 
/*    */     
/* 39 */     if (tokenizedLine != null && tokenizedLine.size() > 1) {
/* 40 */       tokenizedLine.remove((Md5Token)this.endToken);
/* 41 */       b.getTokenizedLines().add(tokenizedLine);
/*    */     } 
/* 43 */     return b;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TokenizedLine getNextTokenizedLine() throws IOException {
/*    */     int tokenType;
/* 52 */     TokenizedLine line = new TokenizedLine();
/*    */     
/*    */     do {
/* 55 */       tokenType = this.stream.nextToken();
/* 56 */       if (tokenType == -3 || tokenType == 34) {
/* 57 */         String val = this.stream.sval;
/* 58 */         if (val.equals(this.endToken.getValue())) {
/* 59 */           line.add((Md5Token)this.endToken);
/*    */         } else {
/* 61 */           line.add((Md5Token)new Md5StringToken(val));
/*    */         } 
/* 63 */       } else if (tokenType == -2) {
/* 64 */         line.add((Md5Token)new Md5NumberToken(this.stream.nval));
/*    */       } 
/* 66 */     } while (tokenType != -1 && (tokenType != 10 || line.size() <= 0));
/*    */     
/* 68 */     if (line.size() == 0) {
/* 69 */       return null;
/*    */     }
/* 71 */     return line;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\importer\resource\Md5Stream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */