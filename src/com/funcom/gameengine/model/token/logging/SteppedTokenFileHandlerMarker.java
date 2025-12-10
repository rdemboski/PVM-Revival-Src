/*    */ package com.funcom.gameengine.model.token.logging;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.logging.FileHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class SteppedTokenFileHandlerMarker
/*    */   extends FileHandler
/*    */ {
/*    */   public SteppedTokenFileHandlerMarker() throws IOException, SecurityException {}
/*    */   
/*    */   public SteppedTokenFileHandlerMarker(String pattern) throws IOException, SecurityException {
/* 16 */     super(pattern);
/*    */   }
/*    */   
/*    */   public SteppedTokenFileHandlerMarker(String pattern, boolean append) throws IOException, SecurityException {
/* 20 */     super(pattern, append);
/*    */   }
/*    */   
/*    */   public SteppedTokenFileHandlerMarker(String pattern, int limit, int count) throws IOException, SecurityException {
/* 24 */     super(pattern, limit, count);
/*    */   }
/*    */   
/*    */   public SteppedTokenFileHandlerMarker(String pattern, int limit, int count, boolean append) throws IOException, SecurityException {
/* 28 */     super(pattern, limit, count, append);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\logging\SteppedTokenFileHandlerMarker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */