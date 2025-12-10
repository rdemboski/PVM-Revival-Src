/*    */ package com.funcom.commons;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import javax.swing.JOptionPane;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MessageBox
/*    */ {
/*    */   public static int yesno(String theMessage, String caption) {
/* 29 */     int result = JOptionPane.showConfirmDialog((Component)null, theMessage, caption, 0);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 35 */     return result;
/*    */   }
/*    */   
/*    */   public static int yesnocancel(String theMessage, String caption) {
/* 39 */     int result = JOptionPane.showConfirmDialog((Component)null, theMessage, caption, 1);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 45 */     return result;
/*    */   }
/*    */   
/*    */   public static int okcancel(String theMessage, String caption) {
/* 49 */     int result = JOptionPane.showConfirmDialog((Component)null, theMessage, caption, 2);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 55 */     return result;
/*    */   }
/*    */   
/*    */   public static void ok(String theMessage, String caption) {
/* 59 */     int result = JOptionPane.showConfirmDialog((Component)null, theMessage, caption, -1);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\MessageBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */