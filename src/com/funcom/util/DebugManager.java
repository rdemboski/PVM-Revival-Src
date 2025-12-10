/*    */ package com.funcom.util;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.ComponentOrientation;
/*    */ import java.awt.event.WindowAdapter;
/*    */ import java.awt.event.WindowEvent;
/*    */ import java.io.File;
/*    */ import javax.swing.JDialog;
/*    */ import javax.swing.JOptionPane;
/*    */ 
/*    */ public class DebugManager {
/* 12 */   private static DebugManager instance = new DebugManager();
/*    */   private static final int WAIT_SECS = 10;
/* 14 */   private boolean debugEnabled = ("true".equalsIgnoreCase(System.getProperty("tcg.debug.enablekeys")) && (new File(System.getenv("USERPROFILE") + File.separator + ".funcom" + File.separator + "tcg" + File.separator + "pinkcats.arecool.pvm")).exists());
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static DebugManager getInstance() {
/* 20 */     return instance;
/*    */   }
/*    */   
/*    */   public boolean isDebugEnabled() {
/* 24 */     return this.debugEnabled;
/*    */   }
/*    */   
/*    */   public void setDebugEnabled(boolean debugEnabled) {
/* 28 */     this.debugEnabled = debugEnabled;
/*    */   }
/*    */   
/*    */   public void warn(String message) {
/* 32 */     warn(message, null);
/*    */   }
/*    */   
/*    */   public void warn(String message, Exception e) {
/* 36 */     System.err.println(message);
/* 37 */     if (e == null) {
/* 38 */       e = new Exception("Stackdump");
/*    */     }
/* 40 */     e.printStackTrace();
/* 41 */     if (isDebugEnabled()) {
/* 42 */       showMessage(message);
/* 43 */       showMessage("Click OK and the game will continue in 10 seconds.\n(see console for stacktrace)");
/*    */       
/*    */       try {
/* 46 */         Thread.sleep(10000L);
/* 47 */       } catch (InterruptedException ignore) {}
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void showMessage(String message) {
/* 53 */     JOptionPane pane = new JOptionPane(message, 1, -1);
/*    */ 
/*    */     
/* 56 */     pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
/* 57 */     JDialog dialog = pane.createDialog((Component)null, "Warning");
/* 58 */     dialog.setAlwaysOnTop(true);
/* 59 */     dialog.addWindowListener(new WindowAdapter()
/*    */         {
/*    */           public void windowOpened(WindowEvent e) {
/* 62 */             e.getWindow().toFront();
/*    */           }
/*    */         });
/* 65 */     dialog.setVisible(true);
/* 66 */     dialog.dispose();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funco\\util\DebugManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */