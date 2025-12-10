/*    */ package com.funcom.gameengine.model.token.logging;
/*    */ 
/*    */ import java.util.logging.Formatter;
/*    */ import java.util.logging.LogRecord;
/*    */ 
/*    */ @Deprecated
/*    */ public class TokenLoggerFormatter
/*    */   extends Formatter {
/*    */   public String format(LogRecord record) {
/* 10 */     Object[] objects = record.getParameters();
/* 11 */     if (objects == null || objects.length < 1) {
/* 12 */       return "";
/*    */     }
/*    */     
/* 15 */     TokenLoggerParameter parameter = (TokenLoggerParameter)objects[0];
/*    */     
/* 17 */     StringBuffer sb = new StringBuffer(500);
/* 18 */     sb.append("\"").append(parameter.getClazz().getName()).append("\",");
/* 19 */     sb.append("\"").append(parameter.getTokenType()).append("\",");
/* 20 */     sb.append("\"").append(parameter.getTimeNanos()).append("\",");
/* 21 */     sb.append("\"").append(parameter.getUsedTime()).append("\"\n");
/*    */     
/* 23 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\logging\TokenLoggerFormatter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */