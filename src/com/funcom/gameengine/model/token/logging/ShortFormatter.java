/*    */ package com.funcom.gameengine.model.token.logging;
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
import java.text.FieldPosition;
/*    */ import java.text.MessageFormat;
/*    */ import java.util.Date;
/*    */ import java.util.logging.Formatter;
/*    */ import java.util.logging.LogRecord;
/*    */ 
/*    */ @Deprecated
/*    */ public class ShortFormatter extends Formatter {
/* 13 */   Date dat = new Date();
/*    */   
/*    */   private static final String format = "{0,date,dd.MM.yy} {0,time,HH:mm:ss.SSS}";
/*    */   private MessageFormat formatter;
/* 17 */   private Object[] args = new Object[1];
/*    */ 
/*    */ 
/*    */   
/* 21 */   private String lineSeparator = System.getProperty("line.separator");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized String format(LogRecord record) {
/* 31 */     StringBuilder sb = new StringBuilder();
/* 32 */     this.dat.setTime(record.getMillis());
/* 33 */     this.args[0] = this.dat;
/* 34 */     StringBuffer text = new StringBuffer();
/* 35 */     if (this.formatter == null) {
/* 36 */       this.formatter = new MessageFormat("{0,date,dd.MM.yy} {0,time,HH:mm:ss.SSS}");
/*    */     }
/* 38 */     this.formatter.format(this.args, text, (FieldPosition)null);
/* 39 */     sb.append("[");
/* 40 */     sb.append(text);
/* 41 */     sb.append("] ");
/* 42 */     String message = formatMessage(record);
/* 43 */     sb.append(record.getLevel().getLocalizedName());
/* 44 */     sb.append(": ");
/* 45 */     sb.append(message);
/* 46 */     sb.append(this.lineSeparator);
/* 47 */     if (record.getThrown() != null) {
/*    */       try {
/* 49 */         StringWriter sw = new StringWriter();
/* 50 */         PrintWriter pw = new PrintWriter(sw);
/* 51 */         record.getThrown().printStackTrace(pw);
/* 52 */         pw.close();
/* 53 */         sb.append(sw.toString());
/* 54 */       } catch (Exception ex) {}
/*    */     }
/*    */     
/* 57 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\logging\ShortFormatter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */