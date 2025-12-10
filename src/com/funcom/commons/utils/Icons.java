/*    */ package com.funcom.commons.utils;
/*    */ 
/*    */ import java.awt.Image;
/*    */ import java.net.URL;
/*    */ import javax.swing.ImageIcon;
/*    */ 
/*    */ 
/*    */ public class Icons
/*    */ {
/*    */   private static Icons instance;
/*    */   public ImageIcon up25;
/*    */   public ImageIcon down25;
/*    */   public ImageIcon left25;
/*    */   public ImageIcon right25;
/*    */   public ImageIcon up12;
/*    */   public ImageIcon down12;
/*    */   public ImageIcon left12;
/*    */   public ImageIcon right12;
/*    */   public ImageIcon x25;
/*    */   public ImageIcon pluss25;
/*    */   public ImageIcon x12;
/*    */   public ImageIcon pluss12;
/*    */   public ImageIcon folder12;
/*    */   public ImageIcon floppydisk12;
/*    */   public ImageIcon document12;
/*    */   public ImageIcon documentWithRedX12;
/*    */   public ImageIcon redX12;
/*    */   public ImageIcon sweep12;
/*    */   public ImageIcon greenV12Gif;
/*    */   public ImageIcon redX12Gif;
/*    */   
/*    */   private Icons() {
/* 33 */     init();
/*    */   }
/*    */   
/*    */   private void init() {
/* 37 */     this.left25 = getIcon("com/funcom/commons/toolicons/arrowLeft.png", 25);
/* 38 */     this.right25 = getIcon("com/funcom/commons/toolicons/arrowRight.png", 25);
/* 39 */     this.up25 = getIcon("com/funcom/commons/toolicons/arrowUp.png", 25);
/* 40 */     this.down25 = getIcon("com/funcom/commons/toolicons/arrowDown.png", 25);
/*    */     
/* 42 */     this.left12 = getIcon("com/funcom/commons/toolicons/arrowLeft.png", 12);
/* 43 */     this.right12 = getIcon("com/funcom/commons/toolicons/arrowRight.png", 12);
/* 44 */     this.up12 = getIcon("com/funcom/commons/toolicons/arrowUp.png", 12);
/* 45 */     this.down12 = getIcon("com/funcom/commons/toolicons/arrowDown.png", 12);
/*    */     
/* 47 */     this.x25 = getIcon("com/funcom/commons/toolicons/x.png", 25);
/* 48 */     this.x12 = getIcon("com/funcom/commons/toolicons/x.png", 12);
/*    */     
/* 50 */     this.pluss25 = getIcon("com/funcom/commons/toolicons/+.png", 25);
/* 51 */     this.pluss12 = getIcon("com/funcom/commons/toolicons/+.png", 12);
/*    */     
/* 53 */     this.folder12 = getIcon("com/funcom/commons/toolicons/folder.png", 12);
/* 54 */     this.floppydisk12 = getIcon("com/funcom/commons/toolicons/floppydisk.png", 12);
/* 55 */     this.document12 = getIcon("com/funcom/commons/toolicons/document.png", 12);
/* 56 */     this.documentWithRedX12 = getIcon("com/funcom/commons/toolicons/documentwithredx.png", 12);
/* 57 */     this.redX12 = getIcon("com/funcom/commons/toolicons/redx.png", 12);
/*    */     
/* 59 */     this.sweep12 = getIcon("com/funcom/commons/toolicons/edit-clear.png", 12);
/*    */     
/* 61 */     this.greenV12Gif = getIcon("com/funcom/commons/toolicons/v.gif", 12);
/* 62 */     this.redX12Gif = getIcon("com/funcom/commons/toolicons/x.gif", 12);
/*    */   }
/*    */   
/*    */   private ImageIcon getIcon(String path, int scale) {
/* 66 */     URL url = getFileURLFromJarArchive(path);
/* 67 */     Image i = (new ImageIcon(url)).getImage().getScaledInstance(scale, scale, 4);
/* 68 */     return new ImageIcon(i);
/*    */   }
/*    */   
/*    */   public static URL getFileURLFromJarArchive(String path) {
/* 72 */     return Icons.class.getClassLoader().getResource(path);
/*    */   }
/*    */   
/*    */   public static Icons getInstance() {
/* 76 */     if (instance == null)
/* 77 */       instance = new Icons(); 
/* 78 */     return instance;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\common\\utils\Icons.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */