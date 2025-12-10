/*    */ package com.funcom.server.tracker;
/*    */ 
/*    */ import javax.swing.JOptionPane;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TrackerServerTestMain
/*    */ {
/*    */   public static void main(String[] args) {
/* 10 */     TrackerServer server = new TrackerServer("dummyService");
/* 11 */     server.start();
/*    */     
/* 13 */     JOptionPane.showMessageDialog(null, "Exit Test");
/* 14 */     System.exit(0);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\tracker\TrackerServerTestMain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */