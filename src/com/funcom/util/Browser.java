/*    */ package com.funcom.util;
/*    */ 
/*    */ import java.awt.Desktop;
/*    */ import java.io.IOException;
/*    */ import java.net.URI;
/*    */ 
/*    */ 
/*    */ public class Browser
/*    */ {
/*    */   public static void openUrl(String url) throws IOException {
/* 11 */     String os = System.getProperty("os.name").toLowerCase();
/* 12 */     Runtime rt = Runtime.getRuntime();
/*    */     
/* 14 */     if (os.indexOf("win") >= 0) {
/* 15 */       Desktop dt = Desktop.getDesktop();
/* 16 */       if (dt.isSupported(Desktop.Action.BROWSE)) {
/*    */         try {
/* 18 */           dt.browse(new URI(url));
/* 19 */         } catch (Exception e) {}
/*    */       }
/*    */     }
/* 22 */     else if (os.indexOf("mac") >= 0) {
/* 23 */       rt.exec("open " + url);
/*    */     }
/*    */     else {
/*    */       
/* 27 */       String[] browsers = { "epiphany", "firefox", "mozilla", "konqueror", "netscape", "opera", "links", "lynx" };
/*    */ 
/*    */       
/* 30 */       StringBuffer cmd = new StringBuffer();
/* 31 */       for (int i = 0; i < browsers.length; i++) {
/* 32 */         cmd.append(((i == 0) ? "" : " || ") + browsers[i] + " \"" + url + "\" ");
/*    */       }
/* 34 */       rt.exec(new String[] { "sh", "-c", cmd.toString() });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funco\\util\Browser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */