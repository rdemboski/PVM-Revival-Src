/*    */ package com.funcom.gameengine.errorhandling;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttributedException
/*    */   extends RuntimeException
/*    */ {
/* 11 */   private Map<String, Object> attributes = new HashMap<String, Object>();
/*    */ 
/*    */   
/*    */   public AttributedException() {}
/*    */   
/*    */   public AttributedException(String message) {
/* 17 */     super(message);
/*    */   }
/*    */   
/*    */   public AttributedException(String message, Throwable cause) {
/* 21 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public AttributedException(Throwable cause) {
/* 25 */     super(cause);
/*    */   }
/*    */   
/*    */   public void put(String key, Object value) {
/* 29 */     if (this.attributes == null)
/* 30 */       this.attributes = new HashMap<String, Object>(); 
/* 31 */     this.attributes.put(key, value);
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 35 */     if (this.attributes == null || this.attributes.isEmpty()) {
/* 36 */       return super.getMessage();
/*    */     }
/* 38 */     String originalMessage = super.getMessage();
/* 39 */     StringBuilder stringBuilder = new StringBuilder();
/* 40 */     stringBuilder.append(originalMessage).append("\n");
/* 41 */     stringBuilder.append("Additional data:").append("\n");
/*    */     
/* 43 */     for (Map.Entry<String, Object> entry : this.attributes.entrySet()) {
/* 44 */       stringBuilder.append("--- ").append(entry.getKey()).append(": '").append(entry.getValue()).append("'\n");
/*    */     }
/* 46 */     return stringBuilder.toString();
/*    */   }
/*    */   
/*    */   public static AttributedException wrapOrConvert(Exception e) {
/* 50 */     if (e instanceof AttributedException) {
/* 51 */       return (AttributedException)e;
/*    */     }
/* 53 */     return new AttributedException(e);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\errorhandling\AttributedException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */