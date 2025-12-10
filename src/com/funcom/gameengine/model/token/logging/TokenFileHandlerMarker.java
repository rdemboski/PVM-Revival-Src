/*    */ package com.funcom.gameengine.model.token.logging;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.logging.FileHandler;
/*    */ 
/*    */ @Deprecated
/*    */ public class TokenFileHandlerMarker
/*    */   extends FileHandler
/*    */ {
/*    */   public TokenFileHandlerMarker() throws IOException, SecurityException {}
/*    */   
/*    */   public TokenFileHandlerMarker(String pattern) throws IOException, SecurityException {
/* 13 */     super(pattern);
/*    */   }
/*    */   
/*    */   public TokenFileHandlerMarker(String pattern, boolean append) throws IOException, SecurityException {
/* 17 */     super(pattern, append);
/*    */   }
/*    */   
/*    */   public TokenFileHandlerMarker(String pattern, int limit, int count) throws IOException, SecurityException {
/* 21 */     super(pattern, limit, count);
/*    */   }
/*    */   
/*    */   public TokenFileHandlerMarker(String pattern, int limit, int count, boolean append) throws IOException, SecurityException {
/* 25 */     super(pattern, limit, count, append);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\logging\TokenFileHandlerMarker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */